package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.annotation.TryTransactionId;
import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.dto.approval.*;
import com.tsoftware.qtd.entity.ApprovalProcess;
import com.tsoftware.qtd.entity.ApprovalSetting;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.*;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.ApprovalProcessRepository;
import com.tsoftware.qtd.repository.ApprovalSettingRepository;
import com.tsoftware.qtd.repository.EmployeeRepository;
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
public class ApprovalProcessService {
  private final ApprovalProcessRepository repository;
  private final ApprovalProcessMapper approvalProcessMapper;
  private final ApprovalSettingRepository approvalSettingRepository;
  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final PageResponseMapper pageResponseMapper;
  private final ApplicationMapper applicationMapper;
  private final ApplicationContext applicationContext;
  private final ApprovalMapper approvalMapper;
  private final ApplicationRepository applicationRepository;

  @TryTransactionId
  public ApprovalProcessResponse create(
      Object object, ApplicationRequest applicationRequest, ProcessType type) {
    applicationRepository
        .findById(UUID.fromString(applicationRequest.getId()))
        .orElseThrow(
            () ->
                new CommonException(
                    ErrorType.ENTITY_NOT_FOUND,
                    "Application not found: " + applicationRequest.getId()));
    var exists =
        repository.existsByApplicationIdAndType(UUID.fromString(applicationRequest.getId()), type);
    if (exists) {
      throw new CommonException(
          ErrorType.HAS_APPLICATION_IN_PROGRESS,
          "application: " + applicationRequest.getId(),
          type.name());
    }
    var approvalProcess =
        ApprovalProcessDTO.builder()
            .application(applicationMapper.toDTO(applicationRequest))
            .type(type)
            .status(ActionStatus.WAIT)
            .metadata(object)
            .build();
    this.mappingApprovesFromSetting(approvalProcess, type);
    var entity = approvalProcessMapper.toEntity(approvalProcess);
    Optional.ofNullable(entity.getGroupApprovals())
        .ifPresent(
            stef ->
                stef.forEach(
                    groupApprove -> {
                      groupApprove.setApprovalProcess(entity);
                      groupApprove
                          .getCurrentApprovals()
                          .forEach(
                              approval -> {
                                approval.setGroupApproval(groupApprove);
                              });
                    }));
    Optional.ofNullable(entity.getRoleApprovals())
        .ifPresent(
            stef ->
                stef.forEach(
                    roleApprove -> {
                      roleApprove.setApprovalProcess(entity);
                      roleApprove
                          .getCurrentApprovals()
                          .forEach(
                              approval -> {
                                approval.setRoleApproval(roleApprove);
                              });
                    }));
    Optional.ofNullable(entity.getApprovals())
        .ifPresent(stef -> stef.forEach(approval -> approval.setApprovalProcess(entity)));
    var saved = repository.save(entity);
    return approvalProcessMapper.toResponse(saved);
  }

  public ApprovalProcessResponse approve(UUID id, ApprovalRequest approvalRequest) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    var dto = approvalProcessMapper.toDTO(entity);
    var executor = applicationContext.getBean(dto.getType().getExecutor());
    var approvalProcess = executor.execute(dto, approvalRequest);
    return approvalProcessMapper.toResponse(approvalProcess);
  }

  public PageResponse<ApprovalProcessResponse> getAll(
      Specification<ApprovalProcess> spec, Pageable pageable) {
    try {
      var approvalProcess =
          repository.findAll(spec, pageable).map(approvalProcessMapper::toResponse);
      return pageResponseMapper.toPageResponse(approvalProcess);
    } catch (DataIntegrityViolationException e) {
      throw new CommonException(ErrorType.METHOD_ARGUMENT_NOT_VALID, e.getMessage());
    }
  }

  public ApprovalProcessDTO update(ApprovalProcessDTO approvalProcessDTO) {
    ApprovalProcess entity =
        repository
            .findById(approvalProcessDTO.getId())
            .orElseThrow(
                () -> new CommonException(ErrorType.ENTITY_NOT_FOUND, approvalProcessDTO.getId()));
    approvalProcessMapper.updateEntity(entity, approvalProcessDTO);
    return approvalProcessMapper.toDTO(repository.save(entity));
  }

  public void validateApprovalProcess(ApprovalProcessDTO approvalProcessDTO) {
    if (approvalProcessDTO.getStatus().equals(ActionStatus.APPROVED)) {
      throw new CommonException(
          ErrorType.ACTION_ALREADY_COMPLETED, "Transaction has been approved");
    }
    var userId = RequestUtil.getUserId();
    // Check permission in Approves
    boolean hasPermission =
        Optional.ofNullable(approvalProcessDTO.getApprovals()).orElse(new ArrayList<>()).stream()
            .anyMatch(
                approval -> {
                  var employee = approval.getApprover();
                  return userId.equals(employee.getUserId());
                });

    // If you don't have permission, check in GroupApproves
    if (!hasPermission) {
      hasPermission =
          Optional.ofNullable(approvalProcessDTO.getGroupApprovals())
              .orElse(new ArrayList<>())
              .stream()
              .flatMap(
                  groupApprove ->
                      Optional.ofNullable(groupApprove.getCurrentApprovals())
                          .orElse(new ArrayList<>())
                          .stream())
              .anyMatch(
                  approval -> {
                    var employee = approval.getApprover();
                    return userId.equals(employee.getUserId());
                  });
    }

    // If you don't have permission, check in RoleApproves
    if (!hasPermission) {
      hasPermission =
          Optional.ofNullable(approvalProcessDTO.getRoleApprovals())
              .orElse(new ArrayList<>())
              .stream()
              .flatMap(
                  roleApprove ->
                      Optional.ofNullable(roleApprove.getCurrentApprovals())
                          .orElse(new ArrayList<>())
                          .stream())
              .anyMatch(
                  approval -> {
                    var employee = approval.getApprover();
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

  public void processApproval(
      ApprovalProcessDTO approvalProcessDTO, ApprovalRequest approvalRequest) {
    var approvalDTO = approvalMapper.toDTO(approvalRequest);

    var userId = RequestUtil.getUserId();
    var approvers =
        Optional.ofNullable(approvalProcessDTO.getApprovals()).orElse(new ArrayList<>());
    approvers.stream()
        .filter(approval -> approval.getApprover().getUserId().equals(userId))
        .forEach(
            approval -> {
              approval.setStatus(approvalDTO.getStatus());
              approval.setComment(approvalDTO.getComment());
            });
    var groupApproves =
        Optional.ofNullable(approvalProcessDTO.getGroupApprovals()).orElse(new ArrayList<>());
    groupApproves.forEach(
        groupApprove ->
            Optional.ofNullable(groupApprove.getCurrentApprovals())
                .orElse(new ArrayList<>())
                .stream()
                .filter(approval -> approval.getApprover().getUserId().equals(userId))
                .forEach(
                    approval -> {
                      approval.setStatus(approvalDTO.getStatus());
                      approval.setComment(approvalDTO.getComment());
                    }));
    var roleApprovers =
        Optional.ofNullable(approvalProcessDTO.getRoleApprovals()).orElse(new ArrayList<>());
    roleApprovers.forEach(
        roleApprove ->
            Optional.ofNullable(roleApprove.getCurrentApprovals())
                .orElse(new ArrayList<>())
                .stream()
                .filter(approval -> approval.getApprover().getUserId().equals(userId))
                .forEach(
                    approval -> {
                      approval.setStatus(approvalDTO.getStatus());
                      approval.setComment(approvalDTO.getComment());
                    }));
  }

  private void mappingApprovesFromSetting(
      ApprovalProcessDTO approvalProcess, ProcessType processType) {
    var approveSetting =
        approvalSettingRepository
            .findByProcessType(processType)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "ApprovalSetting with processType: " + processType.name()));
    approvalProcess.setRoleApprovals(this.toRoleApprove(approveSetting, approvalProcess));
    approvalProcess.setGroupApprovals(this.toGroupApprove(approveSetting, approvalProcess));
  }

  private List<GroupApprovalDTO> toGroupApprove(
      ApprovalSetting approvalSetting, ApprovalProcessDTO approvalProcess) {
    List<GroupApprovalDTO> groupApproves = new ArrayList<>();
    approvalSetting
        .getGroupApprovalSettings()
        .forEach(
            groupApproveSetting -> {
              List<ApprovalDTO> approvals = new ArrayList<>();
              var employees = employeeRepository.findByGroupsId(groupApproveSetting.getGroupId());
              employees.forEach(
                  employee -> {
                    var approval =
                        ApprovalDTO.builder()
                            .processType(approvalSetting.getProcessType())
                            .approver(employeeMapper.toEmployeeResponse(employee))
                            .status(ActionStatus.WAIT)
                            .build();
                    approvals.add(approval);
                  });
              groupApproves.add(
                  GroupApprovalDTO.builder()
                      .groupId(groupApproveSetting.getId())
                      .requiredPercentage(groupApproveSetting.getRequiredPercentage())
                      .status(ActionStatus.WAIT)
                      .currentApprovals(approvals)
                      .approvalProcess(
                          ApprovalProcessRequest.builder().id(approvalProcess.getId()).build())
                      .build());
            });
    return groupApproves;
  }

  private List<RoleApprovalDTO> toRoleApprove(
      ApprovalSetting approvalSetting, ApprovalProcessDTO approvalProcess) {
    List<RoleApprovalDTO> roleApproves = new ArrayList<>();
    approvalSetting
        .getRoleApprovalSettings()
        .forEach(
            roleApproveSetting -> {
              List<ApprovalDTO> approvals = new ArrayList<>();
              var employees =
                  employeeRepository.findByRolesName(roleApproveSetting.getRole().name());
              employees.forEach(
                  employee -> {
                    var approval =
                        ApprovalDTO.builder()
                            .approver(employeeMapper.toEmployeeResponse(employee))
                            .processType(approvalSetting.getProcessType())
                            .status(ActionStatus.WAIT)
                            .build();
                    approvals.add(approval);
                  });
              roleApproves.add(
                  RoleApprovalDTO.builder()
                      .approvalProcess(
                          ApprovalProcessRequest.builder().id(approvalProcess.getId()).build())
                      .role(roleApproveSetting.getRole())
                      .currentApprovals(approvals)
                      .requiredCount(roleApproveSetting.getRequiredCount())
                      .status(ActionStatus.WAIT)
                      .build());
            });
    return roleApproves;
  }

  public ApprovalProcessDTO getDTOById(UUID approvalProcessId) {
    var entity =
        repository
            .findById(approvalProcessId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, approvalProcessId));
    return approvalProcessMapper.toDTO(entity);
  }
}
