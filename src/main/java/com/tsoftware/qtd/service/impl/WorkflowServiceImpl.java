package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import com.tsoftware.qtd.dto.workflow.OnboardingWorkflowDTO;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

  private final WorkflowProperties workflowProperties;

  @Override
  public Workflow<?> getByTargetIdAndStatus(UUID targetId, WorkflowStatus status) {
    return null;
  }

  @Override
  public Workflow<?> getByTransactionId(UUID transactionId) {
    return null;
  }

  @Override
  public Workflow<?> save(Workflow<?> workflow) {
    return null;
  }

  @Override
  public Workflow<?> init(UUID targetId) {
    return OnboardingWorkflowDTO.builder().build();
  }

  @Override
  public Workflow<?> get(UUID id) {
    return null;
  }

  @Override
  public void validateStep(Workflow<?> workflow, String step) {
    var nextStepRule =
        CollectionUtils.findFirst(workflowProperties.getOnboarding(), s -> s.getStep().equals(step))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid step (step not in workflow definition)"));
    workflow.getNextSteps().stream()
        .filter(s -> s.equals(step))
        .findFirst()
        .orElseThrow(
            () ->
                new WorkflowException(
                    HttpStatus.BAD_REQUEST.value(), "Invalid step (step not in next steps)"));
    var dependencySteps =
        nextStepRule.getDependencies().stream()
            .map(
                s ->
                    CollectionUtils.findFirst(workflow.getSteps(), st -> st.getName().equals(s))
                        .orElseThrow(
                            () ->
                                new WorkflowException(
                                    HttpStatus.BAD_REQUEST.value(),
                                    "Invalid step (step not in workflow definition)")));
    dependencySteps.forEach(
        s -> {
          if (!WorkflowStatus.COMPLETED.equals(s.getStatus())) {
            throw new WorkflowException(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Dependency step not completed ("
                    + s.getName()
                    + ":"
                    + s.getStatus().getShortname()
                    + ")");
          }
        });
  }

  @Override
  public List<String> calculateNextSteps(Step step) {
    return workflowProperties.getOnboarding().stream()
        .filter(workflowDefinition -> workflowDefinition.getStep().equals(step.getName()))
        .findFirst()
        .orElseThrow(
            () -> new WorkflowException(HttpStatus.BAD_REQUEST.value(), "step rule not found"))
        .getNextSteps()
        .stream()
        .filter(nextStepRule -> evaluateCondition(nextStepRule.getCondition(), step.getMetadata()))
        .map(WorkflowProperties.NextStepRule::getStep)
        .toList();
  }

  private boolean evaluateCondition(String condition, Map<String, Object> metadata) {
    if (condition == null || condition.isEmpty()) {
      return true;
    }
    ExpressionParser parser = new SpelExpressionParser();
    var context = new StandardEvaluationContext();
    context.setVariable("stepMetadata", metadata);
    var exp = parser.parseExpression(condition);
    return Boolean.TRUE.equals(exp.getValue(context, Boolean.class));
  }
}
