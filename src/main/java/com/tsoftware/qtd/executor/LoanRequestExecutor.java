package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.service.LoanRequestService;
import com.tsoftware.qtd.service.WorkflowTransactionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanRequestExecutor")
@RequiredArgsConstructor
public class LoanRequestExecutor extends BaseTransactionExecutor<WorkflowTransactionDTO> {
  final LoanRequestMapper loanRequestMapper;
  private final LoanRequestService loanRequestService;
  final WorkflowTransactionService workflowTransactionService;

  @Override
  protected void preValidate(WorkflowTransactionDTO workflowTransactionDTO) {
    workflowTransactionService.validateTransaction(workflowTransactionDTO);
  }

  @Override
  protected WorkflowTransactionDTO processApproval(
      WorkflowTransactionDTO workflowTransactionDTO, ActionStatus status) {
    return workflowTransactionService.processApproval(workflowTransactionDTO, status);
  }

  @Override
  protected void doExecute(WorkflowTransactionDTO workflowTransactionDTO) {
    log.info("All approvals received for workflowTransaction: {}", workflowTransactionDTO.getId());
    var request =
        JsonParser.convert(workflowTransactionDTO.getMetadata(), LoanRequestRequest.class);
    var result =
        loanRequestService.create(request, UUID.fromString(request.getApplication().getId()));
    workflowTransactionDTO.setReferenceId(result.getId());
  }

  @Override
  protected WorkflowTransactionDTO postExecute(WorkflowTransactionDTO workflowTransactionDTO) {
    return workflowTransactionService.updateTransaction(workflowTransactionDTO);
  }
}
