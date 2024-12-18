package com.tsoftware.qtd.service;

import com.tsoftware.commonlib.constant.WorkflowStatus;
import com.tsoftware.commonlib.context.WorkflowContext;
import com.tsoftware.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.ApproveRequest;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.Transaction;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
  final TransactionExecutorRegistry registry;
  final TransactionRepository repository;
  final DtoMapper mapper;

  public Object approve(ApproveRequest approveRequest) {
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
      WorkflowContext.setStatus(WorkflowStatus.DENIED);
      return ApproveResponse.builder()
          .transactionId(transaction.getId())
          .status(ApproveStatus.REJECTED)
          .build();
    }
    return registry.getExecutor(transaction.getType()).execute(transaction);
  }
}
