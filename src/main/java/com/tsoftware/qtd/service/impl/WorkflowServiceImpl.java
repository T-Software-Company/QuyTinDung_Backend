package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.commonlib.constant.StepType;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import com.tsoftware.qtd.dto.workflow.OnboardingWorkflowDTO;
import com.tsoftware.qtd.dto.workflow.StepHistoryDTO;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.OnboardingWorkflowMapper;
import com.tsoftware.qtd.repository.OnboardingWorkflowRepository;
import com.tsoftware.qtd.util.RequestUtil;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowServiceImpl implements WorkflowService {
  private final OnboardingWorkflowRepository onboardingWorkflowRepository;
  private final WorkflowProperties workflowProperties;
  private final OnboardingWorkflowMapper onboardingWorkflowMapper;

  @Override
  public List<Workflow<?>> getByTargetIdAndStatus(UUID targetId, WorkflowStatus status) {
    var onboardingWorkflow = onboardingWorkflowRepository.findByTargetIdAndStatus(targetId, status);
    List<Workflow<?>> workflows = new ArrayList<>();
    onboardingWorkflow.forEach(workflow -> workflows.add(onboardingWorkflowMapper.toDTO(workflow)));
    return workflows;
  }

  @Override
  public Workflow<?> getByTransactionId(UUID transactionId) {
    var onboardingWorkflow =
        onboardingWorkflowRepository
            .findByStepTransactionId()
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "(findByStepTransactionId: " + transactionId + ")"));
    return onboardingWorkflowMapper.toDTO(onboardingWorkflow);
  }

  @Override
  public Workflow<?> save(Workflow<?> workflow) {
    return null;
  }

  @Override
  public Workflow<?> init(UUID targetId) {
    var step =
        StepHistoryDTO.builder()
            .name(workflowProperties.getOnboarding().getFirst().getStep())
            .status(WorkflowStatus.INPROGRESS)
            .startTime(ZonedDateTime.now())
            .metadata(new HashMap<>())
            .nextSteps(new ArrayList<>())
            .type(StepType.DEFAULT)
            .build();
    return OnboardingWorkflowDTO.builder()
        .targetId(targetId)
        .status(WorkflowStatus.INPROGRESS)
        .startTime(ZonedDateTime.now())
        .steps(new ArrayList<>(List.of(step)))
        .nextSteps(new ArrayList<>())
        .createdBy(RequestUtil.getUserId())
        .build();
  }

  @Override
  public Workflow<?> get(UUID id) {
    var onboardingWorkflow =
        onboardingWorkflowRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    return onboardingWorkflowMapper.toDTO(onboardingWorkflow);
  }

  @Override
  public void validateNextStep(Workflow<?> workflow, String nextStep) {
    var nextStepRule =
        CollectionUtils.findFirst(
                workflowProperties.getOnboarding(), s -> s.getStep().equals(nextStep))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid step (step not in workflow definition)"));
    workflow.getNextSteps().stream()
        .filter(s -> s.equals(nextStep))
        .findFirst()
        .orElseThrow(
            () ->
                new WorkflowException(
                    HttpStatus.BAD_REQUEST.value(), "Invalid step (step not in next steps)"));
    var dependencyStepsOfNextStep =
        nextStepRule.getDependencies().stream()
            .map(
                stepName ->
                    CollectionUtils.findFirst(
                            workflow.getSteps(), st -> st.getName().equals(stepName))
                        .orElseThrow(
                            () ->
                                new WorkflowException(
                                    HttpStatus.BAD_REQUEST.value(),
                                    "Invalid step (no dependence step "
                                        + stepName
                                        + "  in workflow )")));
    dependencyStepsOfNextStep.forEach(
        st -> {
          if (!WorkflowStatus.COMPLETED.equals(st.getStatus())) {
            throw new WorkflowException(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Dependency step not completed ("
                    + st.getName()
                    + ":"
                    + st.getStatus().getShortname()
                    + ")");
          }
        });
  }

  @Override
  public void calculateStatus(Workflow<?> workflow, String stepName) {
    //    var step =
    // CollectionUtils.findFirst(workflow.getSteps(),s->s.getName().equals(stepName)).orElseThrow(()->new CommonException(ErrorType.ENTITY_NOT_FOUND,stepName));
    //    step.setStatus(WorkflowStatus.COMPLETED);
  }

  @Override
  public void calculateCurrentSteps(Workflow<?> workflow) {
    var steps = workflow.getSteps();
    var currentSteps = steps.stream().map(Step::getName).collect(Collectors.toList());
    workflowProperties
        .getOnboarding()
        .forEach(
            workflowDefinition ->
                workflowDefinition.getDependencies().forEach(currentSteps::remove));
    workflow.setCurrentSteps(currentSteps);
  }

  @Override
  public void calculateNextSteps(Workflow<?> workflow, String stepName) {
    var steps = workflow.getSteps();
    var nextSteps =
        workflowProperties.getOnboarding().stream()
            .filter(workflowDefinition -> workflowDefinition.getStep().equals(stepName))
            .findFirst()
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.BAD_REQUEST.value(), "Step rule not found"))
            .getNextStepRules()
            .stream()
            .filter(nextStepRule -> evaluateCondition(nextStepRule.getCondition(), workflow))
            .map(WorkflowProperties.NextStepRule::getStep)
            .toList();
    var step =
        CollectionUtils.findFirst(steps, st -> st.getName().equals(stepName))
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, stepName));
    step.setNextSteps(nextSteps);
    workflow.setNextSteps(
        steps.stream()
            .flatMap(st -> st.getNextSteps().stream())
            .distinct()
            .collect(Collectors.toList()));
  }

  private boolean evaluateCondition(Expression condition, Object object) {
    if (condition == null) {
      return true;
    }
    var context = new StandardEvaluationContext(object);
    return Boolean.TRUE.equals(condition.getValue(context, Boolean.class));
  }
}
