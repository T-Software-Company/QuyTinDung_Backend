package com.tsoftware.qtd.commonlib.aspect;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WorkflowAspect {

  private final WorkflowService workflowService;
  private final WorkflowProperties workflowProperties;

  @AfterReturning(value = "@annotation(TryTransactionId)", returning = "response")
  public void afterTryTransactionId(AbstractTransaction<?> response) {
    WorkflowContext.setTransactionId(response.getId());
  }

  @Before(value = "@annotation(WorkflowAPI)", argNames = "joinPoint,workflowAPI")
  private void beforeProceedWorkflow(JoinPoint joinPoint, WorkflowAPI workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();

    if (action.equals(WorkflowAPI.Action.APPROVE)) {
      this.processBeforeWithApprove(joinPoint);
      return;
    }

    this.processBeforeWithDefault(joinPoint, stepName);
  }

  @AfterReturning(
      value = "@annotation(WorkflowAPI)",
      returning = "response",
      argNames = "joinPoint,response,workflowAPI")
  private void afterReturnWorkflow(JoinPoint joinPoint, Object response, WorkflowAPI workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();
    if (response instanceof ApiResponse<?> apiResponse) {
      response = apiResponse.getResult();
    }
    Workflow<?> workflow = WorkflowContext.getWorkflow();
    if (action.equals(WorkflowAPI.Action.APPROVE)) {
      this.processAfterWithApprove(joinPoint, workflow, response);
      return;
    }
    this.processAfterWithDefault(workflow, stepName, response);
  }

  @AfterThrowing(
      value = "@annotation(WorkflowAPI)",
      throwing = "ex",
      argNames = "joinPoint,ex,workflowAPI")
  private void afterThrowWorkflow(JoinPoint joinPoint, Throwable ex, WorkflowAPI workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();
    Workflow<?> workflow = WorkflowContext.getWorkflow();
    if (action.equals(WorkflowAPI.Action.APPROVE)) {
      this.processAfterThrowWithApprove(joinPoint, workflow, ex);
      return;
    }
    this.processAfterThrowWithDefault(joinPoint, workflow, stepName, ex);
  }

  @After(value = "@annotation(WorkflowAPI)")
  public void afterWorkflow() {
    WorkflowContext.clear();
  }

  private void processBeforeWithDefault(JoinPoint joinPoint, String stepName) {
    var targetId = getTargetId(joinPoint);
    // init workflow
    if (stepName.equals(workflowProperties.getOnboarding().getFirst().getStep())) {
      var workflows = workflowService.getByTargetIdAndStatus(targetId, WorkflowStatus.INPROGRESS);
      if (!workflows.isEmpty()) {
        throw new WorkflowException(HttpStatus.CONFLICT.value(), "Has been in progress");
      }
      var workflow = workflowService.init(targetId);
      var step = workflow.getSteps().getFirst();
      var request = this.extractRequest(joinPoint);
      JsonParser.put(step.getMetadata(), "histories[0].request", request, false);
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
    workflowService.validateStep(workflow, stepName);
    var request = this.extractRequest(joinPoint);
    workflow.getSteps().add(workflowService.initStep(stepName));
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    JsonParser.put(step.getMetadata(), "histories[0].request", request, false);
    WorkflowContext.set(workflow);
  }

  private void processBeforeWithApprove(JoinPoint joinPoint) {
    var transactionId = getTransactionId(joinPoint);
    var workflow = workflowService.getByTransactionId(transactionId);
    var step =
        CollectionUtils.findFirst(
                workflow.getSteps(), s -> s.getTransactionId().equals(transactionId))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.NOT_FOUND.value(),
                        "Step not found(transactionId:" + transactionId + ")"));
    var request = this.extractRequest(joinPoint);
    JsonParser.put(step.getMetadata(), "histories[0].request", request, false);
    WorkflowContext.set(workflow);
  }

  private void processAfterWithDefault(Workflow<?> workflow, String stepName, Object response) {
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    step.setTransactionId(WorkflowContext.getTransactionId());
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    JsonParser.put(metadata, "histories[0].response", response, false);
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  private void processAfterWithApprove(JoinPoint joinPoint, Workflow<?> workflow, Object response) {
    var transactionId = this.getTransactionId(joinPoint);
    var step =
        CollectionUtils.findFirst(
                workflow.getSteps(), s -> s.getTransactionId().equals(transactionId))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.NOT_FOUND.value(),
                        "Step not found(transactionId:" + transactionId + ")"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    JsonParser.put(metadata, "histories[0].response", response, false);
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  private void processAfterThrowWithDefault(
      JoinPoint joinPoint, Workflow<?> workflow, String stepName, Throwable ex) {
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    JsonParser.put(metadata, "histories[0].error", ex.getMessage(), false);
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  private void processAfterThrowWithApprove(
      JoinPoint joinPoint, Workflow<?> workflow, Throwable ex) {
    var transactionId = this.getTransactionId(joinPoint);
    var step =
        CollectionUtils.findFirst(
                workflow.getSteps(), s -> s.getTransactionId().equals(transactionId))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.NOT_FOUND.value(),
                        "Step not found(transactionId:" + transactionId + ")"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    JsonParser.put(metadata, "histories[0].error", ex.getMessage(), false);
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  private UUID getTargetId(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Annotation[][] paramAnnotations = method.getParameterAnnotations();
    Object[] args = joinPoint.getArgs();

    for (int i = 0; i < paramAnnotations.length; i++) {
      for (Annotation annotation : paramAnnotations[i]) {
        if (annotation instanceof TargetId) {
          if (args[i] instanceof UUID uuid) {
            return uuid;
          }
          if (args[i] instanceof String str) {
            return UUID.fromString(str);
          }
        }
      }
    }
    return UUID.randomUUID();
  }

  private UUID getTransactionId(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Annotation[][] paramAnnotations = method.getParameterAnnotations();
    Object[] args = joinPoint.getArgs();

    for (int i = 0; i < paramAnnotations.length; i++) {
      for (Annotation annotation : paramAnnotations[i]) {
        if (annotation instanceof TransactionId) {
          if (args[i] instanceof UUID uuid) {
            return uuid;
          }
          if (args[i] instanceof String str) {
            return UUID.fromString(str);
          }
        }
      }
    }
    throw new WorkflowException(HttpStatus.NOT_FOUND.value(), "Transaction id not found");
  }

  private void finalProcess(Workflow<?> workflow, String step) {
    workflowService.calculateCurrentSteps(workflow);
    workflowService.calculateNextSteps(workflow, step);
    workflowService.calculateStatus(workflow, step);
  }

  private Object extractRequest(JoinPoint joinPoint) {
    Map<String, Object> argsMap = new HashMap<>();
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] argNames = codeSignature.getParameterNames();
    Object[] argValues = joinPoint.getArgs();
    for (int i = 0; i < argNames.length; i++) {
      argsMap.put(argNames[i], argValues[i]);
    }
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null) {
      HttpServletRequest request = requestAttributes.getRequest();
      argsMap.put("endpoint", request.getRequestURI());
      argsMap.put("ip", request.getRemoteAddr());
    }
    argsMap.put("time", ZonedDateTime.now());
    argsMap.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
    return argsMap;
  }
}
