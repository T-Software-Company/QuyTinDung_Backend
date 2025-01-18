package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.service.LoanPlanService;
import com.tsoftware.qtd.service.WorkflowTransactionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanPlanExecutor")
@RequiredArgsConstructor
public class LoanPlanExecutor extends BaseTransactionExecutor<WorkflowTransactionDTO> {
  private final LoanPlanService loanPlanService;
  private final WorkflowTransactionService workflowTransactionService;

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
    var request = JsonParser.convert(workflowTransactionDTO.getMetadata(), LoanPlanRequest.class);
    var result = loanPlanService.create(request, UUID.fromString(request.getApplication().getId()));
    workflowTransactionDTO.setReferenceId(result.getId());
  }

  @Override
  protected WorkflowTransactionDTO postExecute(WorkflowTransactionDTO workflowTransactionDTO) {
    return workflowTransactionService.updateTransaction(workflowTransactionDTO);
  }
}
