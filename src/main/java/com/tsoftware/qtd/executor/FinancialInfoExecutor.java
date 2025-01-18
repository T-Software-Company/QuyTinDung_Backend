package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.service.FinancialInfoService;
import com.tsoftware.qtd.service.WorkflowTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("financialInfoExecutor")
@RequiredArgsConstructor
public class FinancialInfoExecutor extends BaseTransactionExecutor<WorkflowTransactionDTO> {
  private final WorkflowTransactionService workflowTransactionService;
  private final FinancialInfoService financialInfoService;

  @Override
  protected void preValidate(WorkflowTransactionDTO workflowTransactionDTO) {
    workflowTransactionService.validateTransaction(workflowTransactionDTO);
  }

  @Override
  protected WorkflowTransactionDTO processApproval(
      WorkflowTransactionDTO workflowTransactionDTO, ApproveStatus status) {
    return workflowTransactionService.processApproval(workflowTransactionDTO, status);
  }

  @Override
  protected void doExecute(WorkflowTransactionDTO workflowTransactionDTO) {
    log.info(
        "All approvals received for workflowTransactionDTO: {}", workflowTransactionDTO.getId());
    var data = JsonParser.convert(workflowTransactionDTO.getMetadata(), FinancialInfoRequest.class);
    FinancialInfoResponse result = financialInfoService.create(data);
    workflowTransactionDTO.setReferenceId(result.getId());
  }

  @Override
  protected WorkflowTransactionDTO postExecute(WorkflowTransactionDTO workflowTransactionDTO) {
    return workflowTransactionService.updateTransaction(workflowTransactionDTO);
  }
}
