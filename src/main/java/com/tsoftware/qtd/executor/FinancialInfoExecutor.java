package com.tsoftware.qtd.executor;

import com.tsoftware.commonlib.context.WorkflowContext;
import com.tsoftware.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.commonlib.util.JsonParser;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.dto.transaction.ApproveDTO;
import com.tsoftware.qtd.dto.transaction.Transaction;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("financialInfoExecutor")
@RequiredArgsConstructor
public class FinancialInfoExecutor extends BaseTransactionExecutor<Transaction> {
  final TransactionService transactionService;
  final ApplicationService applicationService;

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
    var data = JsonParser.convert(transaction.getMetadata(), FinancialInfoDTO.class);
    applicationService.createOrUpdateFinancialInfo(transaction.getApplication().getId(), data);
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
