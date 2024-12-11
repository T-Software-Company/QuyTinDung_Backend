package com.tsoftware.qtd.executor;

import com.tsoftware.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.commonlib.util.JsonParser;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.Transaction;
import com.tsoftware.qtd.dto.credit.LoanRequestDTO;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanRequestExecutor")
public class LoanRequestExecutor extends BaseTransactionExecutor<Transaction> {
  @Resource LoanRequestMapper loanRequestMapper;
  @Resource ApplicationRepository applicationRepository;
  @Resource LoanRequestRepository loanRequestRepository;

  @Override
  protected void preValidate(Transaction transaction) {
    // TODO validate transaction, approver, etc
  }

  @Override
  protected Transaction processApproval(Transaction transaction) {
    // Check approval status
    // TODO replace with employee service
    var approvers = transaction.getApprovers();
    approvers.add("user" + approvers.size());
    return transaction;
  }

  @Override
  protected Object doExecute(Transaction transaction) {
    log.info("All approvals received for transaction: {}", transaction.getId());
    var request = JsonParser.convert(transaction.getMetadata(), LoanRequestDTO.class);
    var applicationId = transaction.getApplicationId();
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, applicationId));
    log.info("Executing loan request: {}", request);
    var entity = loanRequestMapper.toEntity(request);
    entity.setApplication(application);
    loanRequestRepository.save(entity);
    return ApproveResponse.builder().status(ApproveStatus.APPROVED).build();
  }
}
