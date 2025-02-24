package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.constant.StepType;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.workflow.OnboardingWorkflowDTO;
import com.tsoftware.qtd.dto.workflow.OnboardingWorkflowResponse;
import com.tsoftware.qtd.dto.workflow.StepHistoryDTO;
import com.tsoftware.qtd.entity.OnboardingWorkflow;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.OnboardingWorkflowMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.OnboardingWorkflowRepository;
import com.tsoftware.qtd.util.RequestUtil;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OnboardingWorkflowService implements WorkflowService {
  private final OnboardingWorkflowRepository onboardingWorkflowRepository;
  private final WorkflowProperties workflowProperties;
  private final OnboardingWorkflowMapper onboardingWorkflowMapper;
  private final PageResponseMapper pageResponseMapper;
  private final BeanResolver beanFactoryResolver;

  public PageResponse<OnboardingWorkflowResponse> getAll(
      Specification<OnboardingWorkflow> spec, Pageable page) {
    var onboardingWorkflowPage =
        onboardingWorkflowRepository.findAll(spec, page).map(onboardingWorkflowMapper::toResponse);
    return pageResponseMapper.toPageResponse(onboardingWorkflowPage);
  }

  public OnboardingWorkflowResponse getByTargetId(String targetId) {
    var onboardingWorkflow =
        onboardingWorkflowRepository
            .findByTargetId(UUID.fromString(targetId))
            .orElseThrow(
                () -> new CommonException(ErrorType.ENTITY_NOT_FOUND, "targetId: " + targetId));
    return onboardingWorkflowMapper.toResponse(onboardingWorkflow);
  }

  @Override
  public Workflow<?> getByTargetId(UUID targetId) {
    var onboardingWorkflow =
        onboardingWorkflowRepository
            .findByTargetId(targetId)
            .orElseThrow(
                () -> new CommonException(ErrorType.ENTITY_NOT_FOUND, "targetId: " + targetId));
    return onboardingWorkflowMapper.toDTO(onboardingWorkflow);
  }

  @Override
  public Workflow<?> getByTransactionId(UUID transactionId) {
    var onboardingWorkflow =
        onboardingWorkflowRepository
            .findByStepsTransactionId(transactionId)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "(findByStepHistoriesTransactionId: " + transactionId + ")"));
    return onboardingWorkflowMapper.toDTO(onboardingWorkflow);
  }

  @Override
  public Workflow<?> save(Workflow<?> workflow) {
    var onboardingWorkflow = onboardingWorkflowMapper.toEntity((OnboardingWorkflowDTO) workflow);
    onboardingWorkflow.getSteps().forEach(step -> step.setOnboardingWorkflow(onboardingWorkflow));
    return onboardingWorkflowMapper.toDTO(onboardingWorkflowRepository.save(onboardingWorkflow));
  }

  @Override
  public Workflow<?> init() {
    var workflow =
        OnboardingWorkflowDTO.builder()
            .status(WorkflowStatus.INPROGRESS)
            .startTime(ZonedDateTime.now())
            .steps(new ArrayList<>())
            .nextSteps(new ArrayList<>())
            .createdBy(RequestUtil.getUserId())
            .build();
    var step =
        (StepHistoryDTO) this.initStep(workflowProperties.getOnboarding().getFirst().getStep());
    workflow.getSteps().add(step);
    return workflow;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Step> T initStep(String stepName) {
    var type =
        Optional.ofNullable(
                CollectionUtils.findFirst(
                        workflowProperties.getOnboarding(), s -> s.getStep().equals(stepName))
                    .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, stepName))
                    .getType())
            .orElse(StepType.ACTION);
    return (T)
        StepHistoryDTO.builder()
            .name(stepName)
            .status(WorkflowStatus.INPROGRESS)
            .startTime(ZonedDateTime.now())
            .metadata(new HashMap<>())
            .nextSteps(new ArrayList<>())
            .type(type)
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
  public void validateStep(Workflow<?> workflow, String nextStep) {
    var nextStepRule =
        CollectionUtils.findFirst(
                workflowProperties.getOnboarding(), s -> s.getStep().equals(nextStep))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid step (step not in workflow definition)"));
    var steps = new ArrayList<>(workflow.getCurrentSteps());
    steps.addAll(workflow.getNextSteps());
    steps.stream()
        .filter(s -> s.equals(nextStep))
        .findFirst()
        .orElseThrow(
            () ->
                new WorkflowException(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid step (step not in current steps or next steps)"));

    var dependencyStepsOfNextStep =
        workflow.getSteps().stream()
            .filter(st -> nextStepRule.getDependencies().contains(st.getName()));
    var stepsName = workflow.getSteps().stream().map(Step::getName).toList();
    var dependencyStepsNotCreate =
        nextStepRule.getDependencies().stream().filter(s -> !stepsName.contains(s));
    dependencyStepsNotCreate.forEach(
        s -> {
          throw new WorkflowException(
              HttpStatus.UNPROCESSABLE_ENTITY.value(),
              "Dependency step has not been create (" + s + ")");
        });
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
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, stepName));
    var expression =
        CollectionUtils.findFirst(
                workflowProperties.getOnboarding(), t -> t.getStep().equals(stepName))
            .orElseThrow(
                () ->
                    new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found in rules"))
            .getExtractStatus();
    var status = this.handleStatusWithExpression((StepHistoryDTO) step, expression);
    step.setStatus(status);
    if (WorkflowStatus.COMPLETED.equals(status)) {
      step.setEndTime(ZonedDateTime.now());
    }
    var isFinalStep =
        workflow.getSteps().stream()
            .anyMatch(
                st -> st.getName().equals(workflowProperties.getOnboarding().getLast().getStep()));
    var stepsIsCompleted =
        workflow.getSteps().stream()
            .allMatch(st -> WorkflowStatus.COMPLETED.equals(st.getStatus()));
    if (isFinalStep && stepsIsCompleted) {
      workflow.setStatus(WorkflowStatus.COMPLETED);
    }
    workflow.setStatus(WorkflowStatus.INPROGRESS);
  }

  @Override
  public void calculatePrevSteps(Workflow<?> workflow) {
    var steps = workflow.getSteps();
    var prevSteps =
        steps.stream()
            .filter(st -> st.getStatus().equals(WorkflowStatus.COMPLETED))
            .map(Step::getName)
            .distinct()
            .collect(Collectors.toList());
    workflow.setPrevSteps(prevSteps);
  }

  @Override
  public void calculateCurrentSteps(Workflow<?> workflow) {
    var steps = workflow.getSteps();
    var currentSteps = steps.stream().map(Step::getName).distinct().collect(Collectors.toList());
    var prevSteps = workflow.getPrevSteps();
    currentSteps.removeAll(prevSteps);
    var skipSteps = extractSkipSteps(prevSteps, currentSteps);
    currentSteps.removeAll(skipSteps);
    workflow.setCurrentSteps(currentSteps);
  }

  @Override
  public void calculateNextSteps(Workflow<?> workflow, String stepName) {
    var steps = workflow.getSteps();
    var step =
        CollectionUtils.findFirst(steps, st -> st.getName().equals(stepName))
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, stepName));
    var nextSteps =
        workflowProperties.getOnboarding().stream()
            .filter(workflowDefinition -> workflowDefinition.getStep().equals(stepName))
            .findFirst()
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.BAD_REQUEST.value(), "Step rule not found"))
            .getNextStepRules()
            .stream()
            .filter(
                nextStepRule ->
                    evaluateCondition((StepHistoryDTO) step, nextStepRule.getCondition()))
            .map(WorkflowProperties.NextStepRule::getStep)
            .distinct()
            .toList();

    step.setNextSteps(nextSteps);

    var workflowNextSteps =
        steps.stream()
            .flatMap(st -> st.getNextSteps().stream())
            .distinct()
            .collect(Collectors.toList());
    var currentSteps = workflow.getCurrentSteps();
    var prevSteps = workflow.getPrevSteps();
    workflowNextSteps.removeAll(currentSteps);
    workflowNextSteps.removeAll(prevSteps);
    var skipSteps = extractSkipSteps(prevSteps, workflowNextSteps);
    workflowNextSteps.removeAll(skipSteps);
    workflow.setNextSteps(workflowNextSteps);
  }

  private List<String> extractSkipSteps(List<String> prevSteps, List<String> workflowNextSteps) {
    var workflowDefinitionsOfNextSteps =
        workflowNextSteps.stream()
            .map(
                s ->
                    CollectionUtils.findFirst(
                            workflowProperties.getOnboarding(),
                            workflowDefinition -> Objects.equals(workflowDefinition.getStep(), s))
                        .orElseThrow());
    var skipSteps = new ArrayList<String>();
    workflowDefinitionsOfNextSteps.forEach(
        workflowDefinition ->
            workflowDefinition
                .getNextStepRules()
                .forEach(
                    nextStepRule -> {
                      if (prevSteps.contains(nextStepRule.getStep())) {
                        skipSteps.add(workflowDefinition.getStep());
                      }
                    }));
    return skipSteps;
  }

  private boolean evaluateCondition(StepHistoryDTO step, Expression condition) {
    if (condition == null) {
      return true;
    }
    var context = new StandardEvaluationContext();
    context.setVariable("step", step);
    context.setBeanResolver(beanFactoryResolver);
    return Boolean.TRUE.equals(condition.getValue(context, Boolean.class));
  }

  private WorkflowStatus handleStatusWithExpression(StepHistoryDTO step, Expression condition) {
    if (condition == null) {
      return WorkflowStatus.COMPLETED;
    }
    var context = new StandardEvaluationContext();
    context.setVariable("step", step);
    context.setBeanResolver(beanFactoryResolver);
    return condition.getValue(context, WorkflowStatus.class);
  }
}
