package com.tsoftware.qtd.commonlib.strategy;

import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.helper.MetadataManager;
import com.tsoftware.qtd.commonlib.helper.WorkflowAspectExtractor;
import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public abstract class AbstractWorkflowStrategy implements WorkflowStrategy {
  protected final WorkflowService workflowService;
  protected final WorkflowProperties properties;
  protected final MetadataManager metadataManager;
  protected final WorkflowAspectExtractor workflowAspectExtractor;

  protected void finalizeWorkflow(Workflow<?> workflow, String step) {
    workflowService.calculateStatus(workflow, step);
    workflowService.calculatePrevSteps(workflow);
    workflowService.calculateCurrentSteps(workflow);
    workflowService.calculateNextSteps(workflow, step);
    workflowService.save(workflow);
  }

  protected Step findStepByName(Workflow<?> workflow, String stepName) {
    return CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
        .orElseThrow(() -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
  }

  protected Step findStepByTransactionId(Workflow<?> workflow, UUID transactionId) {
    return CollectionUtils.findFirst(
            workflow.getSteps(), s -> transactionId.equals(s.getTransactionId()))
        .orElseThrow(
            () ->
                new WorkflowException(
                    HttpStatus.NOT_FOUND.value(),
                    "Step not found(transactionId:" + transactionId + ")"));
  }
}
