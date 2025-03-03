package com.tsoftware.qtd.commonlib.strategy;

import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.helper.MetadataManager;
import com.tsoftware.qtd.commonlib.helper.WorkflowAspectExtractor;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class UpdateWorkflowStrategy extends AbstractWorkflowStrategy {
  public UpdateWorkflowStrategy(
      WorkflowService workflowService,
      WorkflowProperties properties,
      MetadataManager metadataManager,
      WorkflowAspectExtractor workflowAspectExtractor) {
    super(workflowService, properties, metadataManager, workflowAspectExtractor);
  }

  @Override
  public void beforeProcess(JoinPoint joinPoint, String stepName) {
    var transactionId = this.workflowAspectExtractor.extractTransactionId(joinPoint);
    var workflow = workflowService.getByTransactionId(transactionId);
    workflowService.validateWorkflow(workflow);
    var step = this.findStepByTransactionId(workflow, transactionId);
    var request = this.workflowAspectExtractor.extractRequest(joinPoint);
    var metadata = step.getMetadata();
    this.metadataManager.updateHistoryRequest(
        metadata, request, WorkflowEngine.WorkflowAction.APPROVE.getValue());
    WorkflowContext.setWorkflow(workflow);
    WorkflowContext.setStep(step);
  }

  @Override
  public void afterProcess(Object response) {
    var workflow = WorkflowContext.getWorkflow();
    var step = WorkflowContext.getStep();
    Map<String, Object> metadata = step.getMetadata();
    this.metadataManager.updateHistoryResponse(
        metadata, this.workflowAspectExtractor.extractResponse(response));
    this.finalizeWorkflow(workflow, step.getName());
  }

  @Override
  public void afterThrowProcess(Throwable ex) {
    var workflow = WorkflowContext.getWorkflow();
    var step = WorkflowContext.getStep();
    var stepName = step.getName();
    Map<String, Object> metadata = step.getMetadata();
    this.metadataManager.updateHistoryError(
        metadata, this.workflowAspectExtractor.extractErrorMessage(ex));
    this.finalizeWorkflow(workflow, stepName);
  }
}
