package com.tsoftware.qtd.commonlib.strategy;

import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.helper.MetadataManager;
import com.tsoftware.qtd.commonlib.helper.WorkflowAspectExtractor;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class CancelWorkflowStrategy extends AbstractWorkflowStrategy {

  public CancelWorkflowStrategy(
      WorkflowService workflowService,
      WorkflowProperties properties,
      MetadataManager metadataManager,
      WorkflowAspectExtractor workflowAspectExtractor) {
    super(workflowService, properties, metadataManager, workflowAspectExtractor);
  }

  @Override
  public void beforeProcess(JoinPoint joinPoint, String stepName) {
    var targetId = this.workflowAspectExtractor.extractTargetId(joinPoint);
    var workflow = workflowService.getByTargetId(targetId);
    workflowService.validateWorkflow(workflow);
    var request = this.workflowAspectExtractor.extractRequest(joinPoint);
    var metadata = Optional.ofNullable(workflow.getMetadata()).orElse(new HashMap<>());
    this.metadataManager.updateHistoryRequest(
        metadata, request, WorkflowEngine.WorkflowAction.CANCEL.getValue());
    workflow.setStatus(WorkflowStatus.CANCELLED);
    workflow.setMetadata(metadata);
    WorkflowContext.setWorkflow(workflow);
  }

  @Override
  public void afterProcess(Object response) {
    var workflow = WorkflowContext.getWorkflow();
    var metadata = workflow.getMetadata();
    this.metadataManager.updateHistoryResponse(
        metadata, this.workflowAspectExtractor.extractResponse(response));
    workflowService.save(workflow);
  }

  @Override
  public void afterThrowProcess(Throwable ex) {
    var workflow = WorkflowContext.getWorkflow();
    Map<String, Object> metadata = workflow.getMetadata();
    this.metadataManager.updateHistoryError(
        metadata, this.workflowAspectExtractor.extractErrorMessage(ex));
    workflowService.save(workflow);
  }
}
