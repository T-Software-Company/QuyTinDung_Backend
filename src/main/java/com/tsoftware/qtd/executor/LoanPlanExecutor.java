package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.mapper.LoanPlanMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanPlanExecutor")
@RequiredArgsConstructor
public class LoanPlanExecutor extends BaseTransactionExecutor<WorkflowTransactionDTO> {
  final LoanPlanMapper loanPlanMapper;
  final ApplicationRepository applicationRepository;
  final LoanPlanRepository loanPlanRepository;
  final ApplicationService applicationService;
  private final TransactionService transactionService;

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
    var request = JsonParser.convert(workflowTransactionDTO.getMetadata(), LoanPlanDTO.class);
    applicationService.createOrUpdateLoanPlan(
        workflowTransactionDTO.getApplication().getId(), request);
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
