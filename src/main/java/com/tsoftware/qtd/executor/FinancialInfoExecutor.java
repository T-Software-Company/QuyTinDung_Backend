package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("financialInfoExecutor")
@RequiredArgsConstructor
public class FinancialInfoExecutor extends BaseTransactionExecutor<WorkflowTransactionDTO> {
  final TransactionService transactionService;
  final ApplicationService applicationService;

  @Override
  protected void preValidate(WorkflowTransactionDTO workflowTransactionDTO) {
    transactionService.validateTransaction(workflowTransactionDTO);
  }

  @Override
  protected WorkflowTransactionDTO processApproval(WorkflowTransactionDTO workflowTransactionDTO) {
    return transactionService.processApproval(workflowTransactionDTO);
  }

  @Override
  protected Object doExecute(WorkflowTransactionDTO workflowTransactionDTO) {
    log.info(
        "All approvals received for workflowTransactionDTO: {}", workflowTransactionDTO.getId());
    var data = JsonParser.convert(workflowTransactionDTO.getMetadata(), FinancialInfoDTO.class);
    applicationService.createOrUpdateFinancialInfo(
        workflowTransactionDTO.getApplication().getId(), data);
    ApproveResponse response = new ApproveResponse();
    //    response.setData(
    //        ApproveDTO.builder()
    //            .transactionId(workflowTransactionDTO.getId())
    //            .status(ApproveStatus.APPROVED)
    //            .build());
    return response;
  }

  @Override
  protected void postExecute(WorkflowTransactionDTO workflowTransactionDTO) {
    transactionService.updateTransaction(workflowTransactionDTO);
    WorkflowContext.putMetadata(
        workflowTransactionDTO.getId().toString(), workflowTransactionDTO.getStatus());
  }
}
