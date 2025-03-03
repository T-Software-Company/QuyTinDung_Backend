package com.tsoftware.qtd.commonlib.strategy;

import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.constant.StepType;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.helper.MetadataManager;
import com.tsoftware.qtd.commonlib.helper.WorkflowAspectExtractor;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkflowStrategy extends AbstractWorkflowStrategy {
  public CreateWorkflowStrategy(
      WorkflowService workflowService,
      WorkflowProperties properties,
      MetadataManager metadataManager,
      WorkflowAspectExtractor workflowAspectExtractor) {
    super(workflowService, properties, metadataManager, workflowAspectExtractor);
  }

  @Override
  public void beforeProcess(JoinPoint joinPoint, String stepName) {

    // init workflow
    if (stepName.equals(this.properties.getOnboarding().getFirst().getStep())) {
      var workflow = workflowService.init();
      var step = workflow.getSteps().getFirst();
      var request = this.workflowAspectExtractor.extractRequest(joinPoint);
      var metadata = step.getMetadata();
      this.metadataManager.updateHistoryRequest(
          metadata, request, WorkflowEngine.WorkflowAction.CREATE.getValue());
      WorkflowContext.setWorkflow(workflow);
      WorkflowContext.setStep(step);
      return;
    }

    // process next step
    var targetId = this.workflowAspectExtractor.extractTargetId(joinPoint);
    var workflow = workflowService.getByTargetId(targetId);
    workflowService.validateWorkflow(workflow);
    workflowService.validateStep(workflow, stepName);
    var request = this.workflowAspectExtractor.extractRequest(joinPoint);
    var steps = workflow.getSteps();
    if (CollectionUtils.findFirst(steps, s -> s.getName().equals(stepName)).isEmpty()) {
      steps.add(workflowService.initStep(stepName));
    }
    var step = this.findStepByName(workflow, stepName);
    var metadata = step.getMetadata();
    this.metadataManager.updateHistoryRequest(
        metadata, request, WorkflowEngine.WorkflowAction.CREATE.getValue());
    WorkflowContext.setWorkflow(workflow);
    WorkflowContext.setStep(step);
  }

  @Override
  public void afterProcess(Object response) {
    var workflow = WorkflowContext.getWorkflow();
    var step = WorkflowContext.getStep();
    var stepName = step.getName();
    if (step.getType().equals(StepType.APPROVAL)) {
      var transactionId = WorkflowContext.getTransactionId();
      if (transactionId != null) {
        step.setTransactionId(transactionId);
      } else {
        throw new WorkflowException(500, "Can't try transaction id ");
      }
    }
    Map<String, Object> metadata = step.getMetadata();
    this.metadataManager.updateHistoryResponse(
        metadata, this.workflowAspectExtractor.extractResponse(response));
    if (stepName.equals(this.properties.getOnboarding().getFirst().getStep())) {
      workflow.setTargetId(WorkflowContext.getInitTargetId());
    }
    this.finalizeWorkflow(workflow, stepName);
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
