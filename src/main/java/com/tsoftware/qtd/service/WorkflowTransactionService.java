package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.commonlib.service.TransactionService;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.entity.WorkflowTransaction;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.WorkflowTransactionRepository;
import com.tsoftware.qtd.util.RequestUtil;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkflowTransactionService implements TransactionService {
  final TransactionExecutorRegistry registry;
  final WorkflowTransactionRepository repository;
  final DtoMapper mapper;

  public WorkflowTransactionDTO approve(UUID id, ApproveStatus status) {
    var transactionDTO =
        repository
            .findById(id)
            .map(mapper::toDTO)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    var userId = RequestUtil.getUserId();

    var approves =
        transactionDTO.getApproves().stream()
            .filter(approve -> userId.equals(approve.getApprover().getUserId()))
            .toList();
    if (approves.isEmpty()) {
      throw new CommonException(
          ErrorType.ACCESS_DENIED, "You don't have permission to approve this workflow .");
    } else {
      approves.forEach(approve -> approve.setStatus(status));
    }
    if (transactionDTO.isApproved()) {
      transactionDTO.setStatus(ApproveStatus.APPROVED);
      transactionDTO.setApprovedAt(ZonedDateTime.now());
    }
    return (WorkflowTransactionDTO)
        registry.getExecutor(transactionDTO.getType()).execute(transactionDTO);
  }

  public WorkflowTransactionDTO updateTransaction(WorkflowTransactionDTO workflowTransactionDTO) {
    WorkflowTransaction entity =
        repository
            .findById(workflowTransactionDTO.getId())
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, workflowTransactionDTO.getId()));
    mapper.updateEntity(entity, workflowTransactionDTO);
    return mapper.toDTO(repository.save(entity));
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

  @Override
  @SuppressWarnings("unchecked")
  public <T extends AbstractTransaction<?>> T create(T transaction) {
    var t = repository.save(mapper.toEntity((WorkflowTransactionDTO) transaction));
    return (T) mapper.toDTO(t);
  }
}
