package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.transaction.ApproveDTO;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.Transaction;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanRequestExecutor")
@RequiredArgsConstructor
public class LoanRequestExecutor extends BaseTransactionExecutor<Transaction> {
  final DtoMapper dtoMapper;
  final LoanRequestMapper loanRequestMapper;
  final ApplicationRepository applicationRepository;
  final ApplicationService applicationService;
  final TransactionService transactionService;

  @Override
  protected void preValidate(Transaction transaction) {
    transactionService.validateTransaction(transaction);
  }

  @Override
  protected Transaction processApproval(Transaction transaction) {
    return transactionService.processApproval(transaction);
  }

  @Override
  protected Object doExecute(Transaction transaction) {
    log.info("All approvals received for transaction: {}", transaction.getId());
    var request = JsonParser.convert(transaction.getMetadata(), LoanRequestDTO.class);
    applicationService.createOrUpdateLoanRequest(transaction.getApplication().getId(), request);
    ApproveResponse response = new ApproveResponse();
    response.setData(
        ApproveDTO.builder()
            .transactionId(transaction.getId())
            .status(ApproveStatus.APPROVED)
            .build());
    return response;
  }

  @Override
  protected void postExecute(Transaction transaction) {
    transactionService.updateTransaction(transaction);
    WorkflowContext.putMetadata(transaction.getId().toString(), transaction.getStatus());
  }
}
