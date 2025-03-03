package com.tsoftware.qtd.commonlib.strategy;

import com.tsoftware.qtd.commonlib.helper.MetadataManager;
import com.tsoftware.qtd.commonlib.helper.WorkflowAspectExtractor;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
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
  public void beforeProcess(JoinPoint joinPoint, String stepName) {}

  @Override
  public void afterProcess(Object response) {}

  @Override
  public void afterThrowProcess(Throwable ex) {}
}
