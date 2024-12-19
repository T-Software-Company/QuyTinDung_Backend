package com.tsoftware.qtd.service;

import com.tsoftware.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.ApproveDTO;
import com.tsoftware.qtd.dto.transaction.Transaction;
import com.tsoftware.qtd.entity.TransactionEntity;
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

  public Object approve(ApproveDTO approveRequest) {
    Transaction transaction =
        repository
            .findById(approveRequest.getTransactionId())
            .map(mapper::toDomain)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, approveRequest.getTransactionId()));
    if (ApproveStatus.REJECTED.equals(approveRequest.getStatus())) {
      transaction.setStatus(ApproveStatus.REJECTED);
      repository.save(mapper.toEntity(transaction));
      ApproveResponse response = new ApproveResponse();
      response.setData(
          ApproveDTO.builder()
              .transactionId(transaction.getId())
              .status(ApproveStatus.REJECTED)
              .build());
      return response;
    }
    return registry.getExecutor(transaction.getType()).execute(transaction);
  }

  public void updateTransaction(Transaction transaction) {
    TransactionEntity entity =
        repository
            .findById(transaction.getId())
            .orElseThrow(
                () -> new CommonException(ErrorType.ENTITY_NOT_FOUND, transaction.getId()));
    mapper.updateEntity(entity, transaction);

    List<ApproveDTO> approveDTOS = transaction.getApproves();
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

    if (transaction.isApproved()) {
      entity.setStatus(ApproveStatus.APPROVED);
      entity.setApprovedAt(ZonedDateTime.now());
    }
    repository.save(entity);
  }

  public void validateTransaction(Transaction transaction) {
    var userId = RequestUtil.getUserId();
    boolean hasPermission =
        transaction.getApproves().stream()
            .anyMatch(
                approve -> {
                  var employee = approve.getApprover();
                  return userId.equals(employee.getUserId());
                });
    if (!hasPermission) {
      throw new CommonException(
          ErrorType.ACCESS_DENIED, "You don't have permission to approve this transaction.");
    }

    if (transaction.isApproved()) {
      throw new CommonException(ErrorType.DUPLICATED_REQUEST, "Transaction is already approved.");
    }
  }

  public Transaction processApproval(Transaction transaction) {
    var userId = RequestUtil.getUserId();
    var approvers = Optional.ofNullable(transaction.getApproves()).orElse(new ArrayList<>());
    approvers.stream()
        .filter(approve -> userId.equals(approve.getApprover().getUserId()))
        .forEach(approve -> approve.setStatus(ApproveStatus.APPROVED));
    return transaction;
  }
}
