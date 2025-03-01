package com.tsoftware.qtd.commonlib.helper;

import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkflowValidator {
  private final WorkflowService workflowService;

  public void validateWorkflowAndStep(Workflow<?> workflow, String stepName) {
    workflowService.validateWorkflow(workflow);
    workflowService.validateStep(workflow, stepName);
  }

  public void validateTransactionId(UUID transactionId, Workflow<?> workflow) {
    if (transactionId == null) {
      throw new WorkflowException(500, "Transaction ID not found");
    }
    // Additional validation logic
  }
}
