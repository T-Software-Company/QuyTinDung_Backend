package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import com.tsoftware.qtd.entity.OnboardingWorkflowEntity;
import com.tsoftware.qtd.exception.WorkflowException;
import com.tsoftware.qtd.mapper.WorkflowMapper;
import com.tsoftware.qtd.model.OnboardingWorkflow;
import com.tsoftware.qtd.model.StepHistory;
import com.tsoftware.qtd.model.Workflow;
import com.tsoftware.qtd.model.WorkflowRequest;
import com.tsoftware.qtd.model.WorkflowResponse;
import com.tsoftware.qtd.properties.WorkflowRuleProperties;
import com.tsoftware.qtd.repository.OnboardingWorkflowRepository;
import com.tsoftware.qtd.service.WorkflowStorage;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class WorkflowService implements WorkflowStorage<OnboardingWorkflow> {

  private static final String UNKNOWN_STEP = "unknown";

  @Autowired OnboardingWorkflowRepository onboardingWorkflowRepository;
  @Autowired WorkflowMapper workflowMapper;

  @Autowired protected WorkflowRuleProperties workflowRuleProperties;

  @Autowired private BeanFactoryResolver beanFactoryResolver;

  public Workflow get(UUID targetId) {
    if (targetId == null) {
      return null;
    }
    var lstWorkflows = getByStatus(targetId, WorkflowStatus.INPROGRESS);
    if (CollectionUtils.isEmpty(lstWorkflows)) {
      return null;
    }
    return (Workflow) lstWorkflows.getFirst();
  }

  @SuppressWarnings("unchecked")
  public Workflow save(Workflow workflow, WorkflowStatus recentStatus) {
    var now = new Date();
    if (workflow.getStatus() != recentStatus) {
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
                .status(workflow.getStatus())
                .statusUpdatedTime(workflow.getStatusUpdatedTime())
                .build());
    return save((OnboardingWorkflow) workflow);
  }

  public void validateStep(WorkflowRequest request) {
    var currentStep = request.getCurrentStep();
    var ruleByStep = workflowRuleProperties.getRules().get(currentStep);
    if (ruleByStep == null) {
      throw new WorkflowException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Invalid step.");
    }
  }

  public String calculateNextStep(WorkflowRequest request, WorkflowResponse response) {
    // prepare context for expressions
    var context = new StandardEvaluationContext(response);
    context.setBeanResolver(beanFactoryResolver);
    context.setVariable("request", request);
    context.setVariable("response", response);

    // get rule and run
    var currentStep = request.getCurrentStep();
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

  @Override
  public List<OnboardingWorkflow> getByStatus(UUID targetUUID, WorkflowStatus status) {
    return onboardingWorkflowRepository.findAllByTargetUuidAndStatus(targetUUID, status).stream()
        .map(workflowMapper::toWorkflow)
        .toList();
  }

  @Override
  public OnboardingWorkflow save(OnboardingWorkflow workflow) {
    var entity =
        onboardingWorkflowRepository
            .findByTargetUuid(workflow.getTargetId())
            .orElse(new OnboardingWorkflowEntity());
    workflowMapper.updateEntity(entity, workflow);
    onboardingWorkflowRepository.save(entity);
    return workflow;
  }
}
