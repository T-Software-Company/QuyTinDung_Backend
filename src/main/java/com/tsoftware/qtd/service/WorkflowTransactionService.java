package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.annotation.TryTransactionId;
import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.dto.transaction.*;
import com.tsoftware.qtd.entity.ApproveSetting;
import com.tsoftware.qtd.entity.WorkflowTransaction;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.*;
import com.tsoftware.qtd.repository.ApproveSettingRepository;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.WorkflowTransactionRepository;
import com.tsoftware.qtd.util.RequestUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkflowTransactionService {
  private final WorkflowTransactionRepository repository;
  private final WorkflowTransactionMapper workflowTransactionMapper;
  private final ApproveSettingRepository approveSettingRepository;
  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final PageResponseMapper pageResponseMapper;
  private final ApplicationMapper applicationMapper;
  private final ApplicationContext applicationContext;

  @TryTransactionId
  public WorkflowTransactionResponse create(
      Object object, ApplicationRequest applicationRequest, TransactionType type) {
    var exists =
        repository.existsByApplicationIdAndType(UUID.fromString(applicationRequest.getId()), type);
    if (exists) {
      throw new CommonException(
          ErrorType.HAS_APPLICATION_IN_PROGRESS,
          "application: " + applicationRequest.getId(),
          type.name());
    }
    var transaction =
        WorkflowTransactionDTO.builder()
            .application(applicationMapper.toDTO(applicationRequest))
            .type(type)
            .status(ActionStatus.WAIT)
            .metadata(object)
            .build();
    this.mappingApprovesFromSetting(transaction, type);
    var entity = workflowTransactionMapper.toEntity(transaction);
    Optional.ofNullable(entity.getGroupApproves())
        .ifPresent(
            stef ->
                stef.forEach(
                    groupApprove -> {
                      groupApprove.setTransaction(entity);
                      groupApprove
                          .getCurrentApproves()
                          .forEach(approve -> approve.setGroupApprove(groupApprove));
                    }));
    Optional.ofNullable(entity.getRoleApproves())
        .ifPresent(
            stef ->
                stef.forEach(
                    roleApprove -> {
                      roleApprove.setTransaction(entity);
                      roleApprove
                          .getCurrentApproves()
                          .forEach(approve -> approve.setRoleApprove(roleApprove));
                    }));
    Optional.ofNullable(entity.getApproves())
        .ifPresent(stef -> stef.forEach(approve -> approve.setTransaction(entity)));
    var saved = repository.save(entity);
    return workflowTransactionMapper.toResponse(saved);
  }

  public WorkflowTransactionResponse approve(UUID id, ActionStatus status) {
    var transactionDTO =
        repository
            .findById(id)
            .map(workflowTransactionMapper::toDTO)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    var executor = applicationContext.getBean(transactionDTO.getType().getExecutor());
    var transaction = executor.execute(transactionDTO, status);
    return workflowTransactionMapper.toResponse(transaction);
  }

  public PageResponse<WorkflowTransactionResponse> getAll(
      Specification<WorkflowTransaction> spec, Pageable pageable) {
    try {
      var transactions =
          repository.findAll(spec, pageable).map(workflowTransactionMapper::toResponse);
      return pageResponseMapper.toPageResponse(transactions);
    } catch (DataIntegrityViolationException e) {
      throw new CommonException(ErrorType.METHOD_ARGUMENT_NOT_VALID, e.getMessage());
    }
  }

  public WorkflowTransactionDTO updateTransaction(WorkflowTransactionDTO workflowTransactionDTO) {
    WorkflowTransaction entity =
        repository
            .findById(workflowTransactionDTO.getId())
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, workflowTransactionDTO.getId()));
    workflowTransactionMapper.updateEntity(entity, workflowTransactionDTO);
    return workflowTransactionMapper.toDTO(repository.save(entity));
  }

  public void validateTransaction(WorkflowTransactionDTO workflowTransactionDTO) {
    if (workflowTransactionDTO.getStatus().equals(ActionStatus.APPROVED)) {
      throw new CommonException(
          ErrorType.ACTION_ALREADY_COMPLETED, "Transaction has been approved");
    }
    var userId = RequestUtil.getUserId();
    // Check permission in Approves
    boolean hasPermission =
        Optional.ofNullable(workflowTransactionDTO.getApproves()).orElse(new ArrayList<>()).stream()
            .anyMatch(
                approve -> {
                  var employee = approve.getApprover();
                  return userId.equals(employee.getUserId());
                });

    // If you don't have permission, check in GroupApproves
    if (!hasPermission) {
      hasPermission =
          Optional.ofNullable(workflowTransactionDTO.getGroupApproves())
              .orElse(new ArrayList<>())
              .stream()
              .flatMap(
                  groupApprove ->
                      Optional.ofNullable(groupApprove.getCurrentApproves())
                          .orElse(new ArrayList<>())
                          .stream())
              .anyMatch(
                  approve -> {
                    var employee = approve.getApprover();
                    return userId.equals(employee.getUserId());
                  });
    }

    // If you don't have permission, check in RoleApproves
    if (!hasPermission) {
      hasPermission =
          Optional.ofNullable(workflowTransactionDTO.getRoleApproves())
              .orElse(new ArrayList<>())
              .stream()
              .flatMap(
                  roleApprove ->
                      Optional.ofNullable(roleApprove.getCurrentApproves())
                          .orElse(new ArrayList<>())
                          .stream())
              .anyMatch(
                  approve -> {
                    var employee = approve.getApprover();
                    return userId.equals(employee.getUserId());
                  });
    }

    // If there are no permission at all list
    if (!hasPermission) {
      throw new CommonException(
          ErrorType.ACCESS_DENIED,
          "You don't have permission to approve this workflowTransaction.");
    }
  }

  public WorkflowTransactionDTO processApproval(
      WorkflowTransactionDTO workflowTransactionDTO, ActionStatus status) {
    var userId = RequestUtil.getUserId();
    var approvers =
        Optional.ofNullable(workflowTransactionDTO.getApproves()).orElse(new ArrayList<>());
    approvers.stream()
        .filter(approve -> approve.getApprover().getUserId().equals(userId))
        .forEach(approve -> approve.setStatus(status));
    var groupApproves =
        Optional.ofNullable(workflowTransactionDTO.getGroupApproves()).orElse(new ArrayList<>());
    groupApproves.forEach(
        groupApprove ->
            Optional.ofNullable(groupApprove.getCurrentApproves())
                .orElse(new ArrayList<>())
                .stream()
                .filter(approve -> approve.getApprover().getUserId().equals(userId))
                .forEach(approve -> approve.setStatus(status)));
    var roleApprovers =
        Optional.ofNullable(workflowTransactionDTO.getRoleApproves()).orElse(new ArrayList<>());
    roleApprovers.forEach(
        roleApprove ->
            Optional.ofNullable(roleApprove.getCurrentApproves()).orElse(new ArrayList<>()).stream()
                .filter(approve -> approve.getApprover().getUserId().equals(userId))
                .forEach(approve -> approve.setStatus(status)));

    return workflowTransactionDTO;
  }

  public void mappingApprovesFromSetting(
      WorkflowTransactionDTO transaction, TransactionType transactionType) {
    var approveSetting =
        approveSettingRepository
            .findByTransactionType(transactionType)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "ApproveSetting with transactionType: " + transactionType.name()));
    transaction.setRoleApproves(this.toRoleApprove(approveSetting, transaction));
    transaction.setGroupApproves(this.toGroupApprove(approveSetting, transaction));
  }

  private List<GroupApproveDTO> toGroupApprove(
      ApproveSetting approveSetting, WorkflowTransactionDTO transaction) {
    List<GroupApproveDTO> groupApproves = new ArrayList<>();
    approveSetting
        .getGroupApproveSettings()
        .forEach(
            groupApproveSetting -> {
              List<ApproveDTO> approves = new ArrayList<>();
              var employees = employeeRepository.findByGroupsId(groupApproveSetting.getGroupId());
              employees.forEach(
                  employee -> {
                    var approve =
                        ApproveDTO.builder()
                            .approver(employeeMapper.toEmployeeResponse(employee))
                            .build();
                    approves.add(approve);
                  });
              groupApproves.add(
                  GroupApproveDTO.builder()
                      .transaction(
                          WorkflowTransactionRequest.builder().id(transaction.getId()).build())
                      .groupId(groupApproveSetting.getId())
                      .requiredPercentage(groupApproveSetting.getRequiredPercentage())
                      .status(ActionStatus.WAIT)
                      .currentApproves(approves)
                      .transaction(
                          WorkflowTransactionRequest.builder().id(transaction.getId()).build())
                      .build());
            });
    return groupApproves;
  }

  private List<RoleApproveDTO> toRoleApprove(
      ApproveSetting approveSetting, WorkflowTransactionDTO transaction) {
    List<RoleApproveDTO> roleApproves = new ArrayList<>();
    approveSetting
        .getRoleApproveSettings()
        .forEach(
            roleApproveSetting -> {
              List<ApproveDTO> approves = new ArrayList<>();
              var employees =
                  employeeRepository.findByRolesName(roleApproveSetting.getRole().name());
              employees.forEach(
                  employee -> {
                    var approve =
                        ApproveDTO.builder()
                            .approver(employeeMapper.toEmployeeResponse(employee))
                            .status(ActionStatus.WAIT)
                            .build();
                    approves.add(approve);
                  });
              roleApproves.add(
                  RoleApproveDTO.builder()
                      .transaction(
                          WorkflowTransactionRequest.builder().id(transaction.getId()).build())
                      .role(roleApproveSetting.getRole())
                      .currentApproves(approves)
                      .requiredCount(roleApproveSetting.getRequiredCount())
                      .status(ActionStatus.WAIT)
                      .build());
            });
    return roleApproves;
  }
}
