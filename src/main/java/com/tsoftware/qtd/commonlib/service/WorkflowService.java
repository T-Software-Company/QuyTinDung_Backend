package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.StepHistory;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.model.WorkflowRequest;
import com.tsoftware.qtd.commonlib.properties.WorkflowRuleProperties;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class WorkflowService {

  public static final String UNKNOWN_STEP = "unknown";

  @Autowired protected WorkflowStorage<Workflow> workflowStorage;
  @Autowired protected WorkflowRuleProperties workflowRuleProperties;
  @Autowired protected BeanFactoryResolver beanFactoryResolver;

  public Workflow get(UUID targetId) {
    if (targetId == null) {
      return null;
    }
    var lstWorkflows = workflowStorage.getByStatus(targetId, WorkflowStatus.INPROGRESS);
    if (CollectionUtils.isEmpty(lstWorkflows)) {
      return null;
    }
    return lstWorkflows.getFirst();
  }

  public Workflow save(Workflow workflow, WorkflowStatus recentStatus) {
    var now = new Date();
    if (workflow.getWorkflowStatus() != recentStatus) {
      workflow.setStatusUpdatedTime(ZonedDateTime.now());
    }
    if (CollectionUtils.isEmpty(workflow.getStepHistories())) {
      workflow.setStepHistories(new ArrayList<>());
    }
    workflow
        .getStepHistories()
        .add(
            StepHistory.builder()
                .step(workflow.getCurrentStep())
                .execTime(now)
                .nextStep(workflow.getNextStep())
                .status(workflow.getWorkflowStatus())
                .statusUpdatedTime(workflow.getStatusUpdatedTime())
                .build());
    return workflowStorage.save(workflow);
  }

  public void validateStep(WorkflowRequest request) {
    var currentStep = request.getCurrentStep();
    var ruleByStep = workflowRuleProperties.getRules().get(currentStep);
    if (ruleByStep == null) {
      throw new WorkflowException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Invalid step.");
    }
  }

  public String calculateNextStep(Workflow workflow) {
    // prepare context for expressions
    var context = new StandardEvaluationContext(workflow.getMetadata());
    context.setBeanResolver(beanFactoryResolver);

    // get rule and run
    var currentStep = workflow.getCurrentStep();
    var ruleByStep = workflowRuleProperties.getRules().get(currentStep);
    if (ruleByStep == null) {
      throw new WorkflowException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Invalid step.");
    }
    var optNextStep =
        ruleByStep.entrySet().stream()
            .filter(
                rule ->
                    (boolean) Optional.ofNullable(rule.getValue().getValue(context)).orElse(false))
            .map(Entry::getKey)
            .findFirst();

    return optNextStep.orElse(UNKNOWN_STEP);
  }
}
