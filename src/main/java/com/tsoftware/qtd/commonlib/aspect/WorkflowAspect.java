package com.tsoftware.qtd.commonlib.aspect;

import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WorkflowAspect {

  private final WorkflowService workflowService;

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Pointcut("@annotation(com.tsoftware.qtd.commonlib.annotation.WorkflowAPI)")
  public void workflow() {}

  @Before(value = "@annotation(WorkflowAPI)", argNames = "joinPoint,workflowAPI")
  private void beforeProceedWorkflow(JoinPoint joinPoint, WorkflowAPI workflowAPI) {
    var step = workflowAPI.step();
    var action = workflowAPI.action();
    if (step.equals("init")) {
      WorkflowContext.set(initWorkflow(getTargetId(joinPoint)));
    }
    var targetId = getTargetId(joinPoint);
    Workflow<?> workflow = getOrCreateWorkflow(targetId);
    workflowService.validateStep(workflow, step);
    updateWorkflowStep(workflow);
    WorkflowContext.set(workflow);
  }

  @AfterReturning(value = "@annotation(WorkflowAPI)", returning = "response")
  private void afterReturnWorkflow(JoinPoint joinPoint, Object response) {

    if (response instanceof ApiResponse<?> apiResponse) {
      response = apiResponse.getResult();
    }
    Workflow<?> workflow = WorkflowContext.get();
    if (workflow != null) {
      WorkflowContext.putMetadata();
    }
  }

  @AfterThrowing(value = "@annotation(WorkflowAPI)", throwing = "ex")
  private void afterThrowWorkflow(JoinPoint joinPoint, Throwable ex) {
    //    Workflow<?> workflow = WorkflowContext.get();
    //    if (workflow != null) {
    //      WorkflowContext.putMetadata("error", ex.getMessage());
    //      workflowService.save(workflow);
    //    }
  }

  @After(value = "@annotation(WorkflowAPI)")
  public void afterWorkflow(JoinPoint joinPoint) {
    Workflow<?> workflow = WorkflowContext.get();
    if (workflow != null) {
      workflowService.save(workflow);
    }
    WorkflowContext.clear();
  }

  private UUID getTargetId(JoinPoint joinPoint) {
    return Stream.of(joinPoint.getArgs())
        .filter(arg -> arg instanceof UUID)
        .map(arg -> (UUID) arg)
        .findFirst()
        .orElse(UUID.randomUUID());
  }

  private Workflow<?> getOrCreateWorkflow(UUID targetId) {
    var wf = workflowService.getByTargetIdAndStatus(targetId, WorkflowStatus.INPROGRESS);
    if (wf != null) {
      return wf;
    }
    return initWorkflow(targetId);
  }

  private void updateWorkflowStep(Workflow<?> workflow) {
    if (StringUtils.isNotBlank(workflow.getCurrentStep())
        && StringUtils.isBlank(workflow.getNextStep())) {
      workflow.setCurrentStep(workflow.getNextStep());
    }
  }

  private Workflow<?> initWorkflow(UUID targetId) {
    Workflow<?> workflow = workflowService.init(targetId);
    workflow.setWorkflowStatus(WorkflowStatus.INPROGRESS);
    workflow.setNextSteps(workflowService.calculateNextStep(workflow));
    workflow.setMetadata(new HashMap<>());
    return workflow;
  }
}
