package com.tsoftware.qtd.commonlib.aspect;

import static com.tsoftware.qtd.commonlib.service.WorkflowService.UNKNOWN_STEP;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.commonlib.model.OnboardingWorkflow;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class WorkflowAspect {

  @Autowired private WorkflowService workflowService;

  @Autowired private BeanFactory beanFactory;

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Pointcut("@annotation( com.tsoftware.qtd.commonlib.annotation.WorkflowAPI)")
  public void workflow() {}

  @Before("publicMethod() && workflow()")
  private void beforeProceedWorkflow(JoinPoint joinPoint) {
    var targetId = getTargetId(joinPoint);
    Workflow workflow = getOrCreateWorkflow(targetId);
    updateWorkflowStep(workflow);
    WorkflowContext.set(workflow);
  }

  @AfterReturning(value = "publicMethod() && workflow()", returning = "response")
  private void afterReturnWorkflow(JoinPoint joinPoint, Object response) {
    if (response instanceof ApiResponse<?> apiResponse) {
      response = apiResponse.getResult();
    }
    Workflow workflow = WorkflowContext.get();
    if (workflow != null) {
      WorkflowContext.putMetadata(workflow.getCurrentStep(), response);
      workflow.setNextStep(workflowService.calculateNextStep(workflow));
      workflowService.save(workflow, workflow.getWorkflowStatus());
    }
  }

  @AfterThrowing(value = "publicMethod() && workflow()", throwing = "ex")
  private void afterThrowWorkflow(JoinPoint joinPoint, Throwable ex) {
    Workflow workflow = WorkflowContext.get();
    if (workflow != null) {
      WorkflowContext.putMetadata("error", ex.getMessage());
      workflowService.save(workflow, workflow.getWorkflowStatus());
    }
  }

  @After(value = "publicMethod() && workflow()")
  public void afterWorkflow(JoinPoint joinPoint) {
    WorkflowContext.clear();
  }

  private UUID getTargetId(JoinPoint joinPoint) {
    return Stream.of(joinPoint.getArgs())
        .filter(arg -> arg instanceof UUID)
        .map(arg -> (UUID) arg)
        .findFirst()
        .orElse(UUID.randomUUID());
  }

  private Workflow getOrCreateWorkflow(UUID targetId) {
    return Optional.ofNullable(workflowService.get(targetId))
        .orElseGet(() -> initWorkflow(targetId));
  }

  private void updateWorkflowStep(Workflow workflow) {
    if (StringUtils.isNotBlank(workflow.getCurrentStep())
        && StringUtils.isBlank(workflow.getNextStep())
        && UNKNOWN_STEP.equalsIgnoreCase(workflow.getCurrentStep())) {
      workflow.setCurrentStep(workflow.getNextStep());
    }
  }

  private Workflow initWorkflow(UUID targetTargetUuid) {
    Workflow workflow =
        OnboardingWorkflow.builder().targetId(targetTargetUuid).metadata(new HashMap<>()).build();
    workflow.setCurrentStep(workflowService.calculateNextStep(workflow));
    workflow.setWorkflowStatus(WorkflowStatus.INPROGRESS);
    workflow.setMetadata(new HashMap<>());
    return workflow;
  }
}
