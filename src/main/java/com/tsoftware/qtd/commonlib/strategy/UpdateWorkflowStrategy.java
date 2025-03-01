package com.tsoftware.qtd.commonlib.strategy;

import com.tsoftware.qtd.commonlib.helper.MetadataManager;
import com.tsoftware.qtd.commonlib.helper.WorkflowValidator;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.RequestExtractor;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class UpdateWorkflowStrategy extends AbstractWorkflowStrategy {
  public UpdateWorkflowStrategy(
      WorkflowService workflowService,
      WorkflowProperties properties,
      MetadataManager metadataManager,
      WorkflowValidator validator,
      RequestExtractor requestExtractor) {
    super(workflowService, properties, metadataManager, validator, requestExtractor);
  }

  @Override
  public void beforeProcess(JoinPoint joinPoint, String stepName) {}

  @Override
  public void afterProcess(Workflow<?> workflow, String stepName, Object response) {}

  @Override
  public void afterThrowProcess(Workflow<?> workflow, String stepName, Throwable ex) {}
}
