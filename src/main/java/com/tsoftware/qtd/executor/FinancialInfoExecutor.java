package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.TransactionDTO;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("financialInfoExecutor")
@RequiredArgsConstructor
public class FinancialInfoExecutor extends BaseTransactionExecutor<TransactionDTO> {
  final TransactionService transactionService;
  final ApplicationService applicationService;

  @Override
  protected void preValidate(TransactionDTO transactionDTO) {
    transactionService.validateTransaction(transactionDTO);
  }

  @Override
  protected TransactionDTO processApproval(TransactionDTO transactionDTO) {
    return transactionService.processApproval(transactionDTO);
  }

  @Override
  protected Object doExecute(TransactionDTO transactionDTO) {
    log.info("All approvals received for transactionDTO: {}", transactionDTO.getId());
    var data = JsonParser.convert(transactionDTO.getMetadata(), FinancialInfoDTO.class);
    applicationService.createOrUpdateFinancialInfo(transactionDTO.getApplication().getId(), data);
    ApproveResponse response = new ApproveResponse();
    //    response.setData(
    //        ApproveDTO.builder()
    //            .transactionId(transactionDTO.getId())
    //            .status(ApproveStatus.APPROVED)
    //            .build());
    return response;
  }

  @Override
  protected void postExecute(TransactionDTO transactionDTO) {
    transactionService.updateTransaction(transactionDTO);
    WorkflowContext.putMetadata(transactionDTO.getId().toString(), transactionDTO.getStatus());
  }
}
