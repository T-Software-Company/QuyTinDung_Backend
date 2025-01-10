package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.transaction.ApproveDTO;
import com.tsoftware.qtd.dto.transaction.ApproveRequest;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.entity.WorkflowTransaction;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.TransactionRepository;
import com.tsoftware.qtd.util.RequestUtil;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
  final TransactionExecutorRegistry registry;
  final TransactionRepository repository;
  final DtoMapper mapper;

  public Object approve(ApproveRequest approveRequest) {
    var transactionDTO =
        repository
            .findById(approveRequest.getTransactionId())
            .map(mapper::toDTO)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, approveRequest.getTransactionId()));
    if (ApproveStatus.REJECTED.equals(approveRequest.getStatus())) {
      transactionDTO.setStatus(ApproveStatus.REJECTED);
      repository.save(mapper.toEntity(transactionDTO));
      ApproveResponse response = new ApproveResponse();
      //      response.setData(
      //          ApproveDTO.builder()
      //              .transactionId(transactionDTO.getId())
      //              .status(ApproveStatus.REJECTED)
      //              .build());
      return response;
    }
    return registry.getExecutor(transactionDTO.getType()).execute(transactionDTO);
  }

  public void updateTransaction(WorkflowTransactionDTO workflowTransactionDTO) {
    WorkflowTransaction entity =
        repository
            .findById(workflowTransactionDTO.getId())
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, workflowTransactionDTO.getId()));
    mapper.updateEntity(entity, workflowTransactionDTO);

    List<ApproveDTO> approveDTOS = workflowTransactionDTO.getApproves();
    entity
        .getApproves()
        .forEach(
            approve ->
                approveDTOS.stream()
                    .filter(
                        dto ->
                            dto.getApprover().getUserId().equals(approve.getApprover().getUserId()))
                    .findFirst()
                    .ifPresent(dto -> approve.setStatus(dto.getStatus())));

    if (workflowTransactionDTO.isApproved()) {
      entity.setStatus(ApproveStatus.APPROVED);
      entity.setApprovedAt(ZonedDateTime.now());
    }
    repository.save(entity);
  }

  public void validateTransaction(WorkflowTransactionDTO workflowTransactionDTO) {
    var userId = RequestUtil.getUserId();
    boolean hasPermission =
        workflowTransactionDTO.getApproves().stream()
            .anyMatch(
                approve -> {
                  var employee = approve.getApprover();
                  return userId.equals(employee.getUserId());
                });
    if (!hasPermission) {
      throw new CommonException(
          ErrorType.ACCESS_DENIED,
          "You don't have permission to approve this workflowTransactionDTO.");
    }

    if (workflowTransactionDTO.isApproved()) {
      throw new CommonException(
          ErrorType.DUPLICATED_REQUEST, "WorkflowTransactionDTO is already approved.");
    }
  }

  public WorkflowTransactionDTO processApproval(WorkflowTransactionDTO workflowTransactionDTO) {
    var userId = RequestUtil.getUserId();
    var approvers =
        Optional.ofNullable(workflowTransactionDTO.getApproves()).orElse(new ArrayList<>());
    approvers.stream()
        .filter(approve -> userId.equals(approve.getApprover().getUserId()))
        .forEach(approve -> approve.setStatus(ApproveStatus.APPROVED));
    return workflowTransactionDTO;
  }
}
