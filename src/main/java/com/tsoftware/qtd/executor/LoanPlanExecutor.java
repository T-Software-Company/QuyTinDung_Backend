package com.tsoftware.qtd.executor;

import com.tsoftware.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.commonlib.util.JsonParser;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.Transaction;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.entity.TransactionEntity;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.mapper.LoanPlanMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import com.tsoftware.qtd.repository.TransactionRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanPlanExecutor")
@RequiredArgsConstructor
public class LoanPlanExecutor extends BaseTransactionExecutor<Transaction> {
  final DtoMapper dtoMapper;
  final LoanPlanMapper loanPlanMapper;
  final ApplicationRepository applicationRepository;
  final LoanPlanRepository loanPlanRepository;
  final TransactionRepository transactionRepository;

  @Override
  protected void preValidate(Transaction transaction) {
    // TODO validate transaction, approver, etc
  }

  @Override
  protected Transaction processApproval(Transaction transaction) {
    // Check approval status
    // TODO replace with employee service
    var approvers = Optional.ofNullable(transaction.getApprovers()).orElse(new ArrayList<>());
    approvers.add("user" + approvers.size());
    transaction.setApprovers(approvers);
    return transaction;
  }

  @Override
  protected Object doExecute(Transaction transaction) {
    log.info("All approvals received for transaction: {}", transaction.getId());
    var request = JsonParser.convert(transaction.getMetadata(), LoanPlanDTO.class);
    var applicationId = transaction.getApplicationId();
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, applicationId));
    log.info("Executing loan request: {}", request);
    var entity = loanPlanMapper.toEntity(request);
    entity.setApplication(application);
    loanPlanRepository.save(entity);
    return ApproveResponse.builder()
        .transactionId(transaction.getId())
        .status(ApproveStatus.APPROVED)
        .build();
  }

  @Override
  protected void postExecute(Transaction transaction) {
    TransactionEntity entity =
        transactionRepository
            .findById(transaction.getId())
            .orElseThrow(
                () -> new CommonException(ErrorType.ENTITY_NOT_FOUND, transaction.getId()));
    dtoMapper.updateEntity(entity, transaction);
    if (transaction.isApproved()) {
      entity.setStatus(ApproveStatus.APPROVED);
      entity.setApprovedAt(ZonedDateTime.now());
      entity.setApprovedBy("test");
    }
    transactionRepository.save(entity);
  }
}
