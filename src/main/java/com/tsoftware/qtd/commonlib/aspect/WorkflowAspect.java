package com.tsoftware.qtd.commonlib.aspect;

import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WorkflowAspect {

  private final WorkflowService workflowService;
  private final WorkflowProperties workflowProperties;

  @Pointcut("@annotation(com.tsoftware.qtd.commonlib.annotation.WorkflowAPI)")
  public void workflow() {}

  @Before(value = "@annotation(WorkflowAPI)", argNames = "joinPoint,workflowAPI")
  private void beforeProceedWorkflow(JoinPoint joinPoint, WorkflowAPI workflowAPI) {
    var step = workflowAPI.step();
    var action = workflowAPI.action();
    var targetId = getTargetId(joinPoint);

    // init workflow
    if (step.equals(workflowProperties.getOnboarding().getFirst().getStep())) {
      var workflows = workflowService.getByTargetIdAndStatus(targetId, WorkflowStatus.INPROGRESS);
      if (!workflows.isEmpty()) {
        throw new WorkflowException(HttpStatus.CONFLICT.value(), "Has been in progress");
      }
      var workflow = workflowService.init(targetId);
      WorkflowContext.set(workflow);
      return;
    }

    // approve action
    if (action.equals(WorkflowAPI.Action.APPROVE)) {
      var transactionId = getTransactionId(joinPoint);
      var workflow = workflowService.getByTransactionId(transactionId);
      WorkflowContext.set(workflow);
      return;
    }

    // process next step
    var workflows = workflowService.getByTargetIdAndStatus(targetId, WorkflowStatus.INPROGRESS);
    if (workflows.isEmpty()) {
      throw new WorkflowException(
          HttpStatus.NOT_FOUND.value(), "Work flow not found (targetId: " + targetId + ")");
    }
    var workflow = workflows.getFirst();
    workflowService.validateNextStep(workflow, step);
    WorkflowContext.set(workflow);
  }

  @AfterReturning(value = "@annotation(WorkflowAPI)", returning = "response")
  private void afterReturnWorkflow(Object response, WorkflowAPI workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();
    if (response instanceof ApiResponse<?> apiResponse) {
      response = apiResponse.getResult();
    }
    Workflow<?> workflow = WorkflowContext.get();
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    JsonParser.put(metadata, "histories[0].response", response, false);
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  @AfterThrowing(value = "@annotation(WorkflowAPI)", throwing = "ex")
  private void afterThrowWorkflow(Throwable ex, WorkflowAPI workflowAPI) {
    Workflow<?> workflow = WorkflowContext.get();
    var stepName = workflowAPI.step();
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    //    JsonParser.put(metadata,"histories[0].",response,false);
    workflowService.save(workflow);
  }

  @After(value = "@annotation(WorkflowAPI)")
  public void afterWorkflow() {
    WorkflowContext.clear();
  }

  private UUID getTargetId(JoinPoint joinPoint) {
    return Stream.of(joinPoint.getArgs())
        .filter(arg -> arg instanceof UUID)
        .map(arg -> (UUID) arg)
        .findFirst()
        .orElse(UUID.randomUUID());
  }

  private UUID getTransactionId(JoinPoint joinPoint) {
    return Arrays.stream(joinPoint.getArgs())
        .filter(arg -> arg instanceof UUID)
        .map(arg -> (UUID) arg)
        .findFirst()
        .orElseThrow(
            () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Transaction id not found"));
  }

  private void finalProcess(Workflow<?> workflow, String step) {
    workflowService.calculateCurrentSteps(workflow);
    workflowService.calculateNextSteps(workflow, step);
    workflowService.calculateStatus(workflow, step);
  }
}
