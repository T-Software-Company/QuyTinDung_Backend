package com.tsoftware.qtd.commonlib.aspect;

import static org.springframework.util.ClassUtils.isPrimitiveOrWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import com.tsoftware.qtd.commonlib.annotation.*;
import com.tsoftware.qtd.commonlib.constant.StepType;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.commonlib.model.Workflow;
import com.tsoftware.qtd.commonlib.properties.WorkflowProperties;
import com.tsoftware.qtd.commonlib.service.WorkflowService;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.commonlib.util.StringUtils;
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
import org.springframework.http.ResponseEntity;
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

  @AfterReturning(
      value = "@annotation(tryTransactionId)",
      returning = "response",
      argNames = "response,tryTransactionId")
  public void afterTryTransactionId(Object response, TryTransactionId tryTransactionId)
      throws JsonProcessingException {
    var path = tryTransactionId.path();
    WorkflowContext.setTransactionId(JsonParser.getValueByPath(response, path, UUID.class));
  }

  @AfterReturning(
      value = "@annotation(tryInitTargetId)",
      returning = "response",
      argNames = "response,tryInitTargetId")
  public void afterTryInitTargetId(Object response, TryInitTargetId tryInitTargetId)
      throws JsonProcessingException {
    var path = tryInitTargetId.path();
    WorkflowContext.setInitTargetId(JsonParser.getValueByPath(response, path, UUID.class));
  }

  @Before(value = "@annotation(workflowAPI)", argNames = "joinPoint,workflowAPI")
  public void beforeProceedWorkflow(JoinPoint joinPoint, WorkflowAPI workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();

    if (action.equals(WorkflowAPI.WorkflowAction.CREATE)) {
      this.processBeforeWithCreate(joinPoint, stepName);
      return;
    }
    if (action.equals(WorkflowAPI.WorkflowAction.APPROVE)) {
      this.processBeforeWithApprove(joinPoint);
      return;
    }
    if (action.equals(WorkflowAPI.WorkflowAction.CANCEL)) {
      this.processBeforeWithCancel(joinPoint);
    }
  }

  @AfterReturning(
      value = "@annotation(workflowAPI)",
      returning = "response",
      argNames = "joinPoint,response,workflowAPI")
  private void afterReturnWorkflow(JoinPoint joinPoint, Object response, WorkflowAPI workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();
    if (response instanceof ApiResponse<?> apiResponse) {
      response = apiResponse.getResult();
    }
    Workflow<?> workflow = WorkflowContext.getWorkflow();
    if (action.equals(WorkflowAPI.WorkflowAction.CREATE)) {
      this.processAfterWithCreate(workflow, stepName, response);
    }
    if (action.equals(WorkflowAPI.WorkflowAction.APPROVE)) {
      this.processAfterWithApprove(joinPoint, workflow, response);
      return;
    }
    if (action.equals(WorkflowAPI.WorkflowAction.CANCEL)) {
      this.processAfterWithCancel(workflow, response);
      return;
    }
  }

  @AfterThrowing(
      value = "@annotation(workflowAPI)",
      throwing = "ex",
      argNames = "joinPoint,ex,workflowAPI")
  private void afterThrowWorkflow(JoinPoint joinPoint, Throwable ex, WorkflowAPI workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();
    Workflow<?> workflow = WorkflowContext.getWorkflow();
    if (action.equals(WorkflowAPI.WorkflowAction.CREATE)) {
      this.processAfterThrowWithCreate(workflow, stepName, ex);
    }
    if (action.equals(WorkflowAPI.WorkflowAction.APPROVE)) {
      this.processAfterThrowWithApprove(joinPoint, workflow, ex);
      return;
    }
    if (action.equals(WorkflowAPI.WorkflowAction.CANCEL)) {
      this.processAfterThrowWithCancel(workflow, ex);
      return;
    }
  }

  @After(value = "@annotation(workflowAPI)")
  public void afterWorkflow() {
    WorkflowContext.clear();
  }

  private void processBeforeWithCreate(JoinPoint joinPoint, String stepName) {
    // init workflow
    if (stepName.equals(workflowProperties.getOnboarding().getFirst().getStep())) {
      var workflow = workflowService.init();
      var step = workflow.getSteps().getFirst();
      var request = this.extractRequest(joinPoint);
      var metadata = step.getMetadata();
      JsonParser.put(metadata, "histories[0].request", request);
      JsonParser.put(metadata, "histories[0].action", WorkflowAPI.WorkflowAction.CREATE.getValue());
      WorkflowContext.set(workflow);
      return;
    }
    // process next step

    var targetId = getTargetId(joinPoint);
    var workflow = workflowService.getByTargetId(targetId);
    workflowService.validateWorkflow(workflow);
    workflowService.validateStep(workflow, stepName);
    var request = this.extractRequest(joinPoint);
    var steps = workflow.getSteps();
    if (CollectionUtils.findFirst(steps, s -> s.getName().equals(stepName)).isEmpty()) {
      steps.add(workflowService.initStep(stepName));
    }
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    var metadata = step.getMetadata();
    var histories = metadata.get("histories");
    var index = histories == null ? 0 : JsonPath.parse(histories).read("$.length()", Integer.class);
    JsonParser.put(metadata, "histories[" + index + "].request", request);
    JsonParser.put(
        metadata, "histories[" + index + "].action", WorkflowAPI.WorkflowAction.CREATE.getValue());
    WorkflowContext.set(workflow);
  }

  private void processBeforeWithApprove(JoinPoint joinPoint) {
    var transactionId = getTransactionId(joinPoint);
    var workflow = workflowService.getByTransactionId(transactionId);
    workflowService.validateWorkflow(workflow);
    var step =
        CollectionUtils.findFirst(
                workflow.getSteps(), s -> transactionId.equals(s.getTransactionId()))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.NOT_FOUND.value(),
                        "Step not found(transactionId:" + transactionId + ")"));
    var request = this.extractRequest(joinPoint);
    var metadata = step.getMetadata();
    var index = JsonPath.parse(metadata.get("histories")).read("$.length()", Integer.class);
    JsonParser.put(metadata, "histories[" + index + "].request", request);
    JsonParser.put(
        metadata, "histories[" + index + "].action", WorkflowAPI.WorkflowAction.APPROVE.getValue());
    WorkflowContext.set(workflow);
  }

  private void processBeforeWithCancel(JoinPoint joinPoint) {
    var targetId = getTargetId(joinPoint);
    var workflow = workflowService.getByTargetId(targetId);
    workflowService.validateWorkflow(workflow);
    var request = this.extractRequest(joinPoint);
    var metadata = Optional.ofNullable(workflow.getMetadata()).orElse(new HashMap<>());
    var histories = metadata.get("histories");
    var index = histories == null ? 0 : JsonPath.parse(histories).read("$.length()", Integer.class);
    JsonParser.put(metadata, "histories[" + index + "].request", request);
    JsonParser.put(
        metadata, "histories[" + index + "].action", WorkflowAPI.WorkflowAction.CANCEL.getValue());
    workflow.setStatus(WorkflowStatus.CANCELLED);
    workflow.setMetadata(metadata);
    WorkflowContext.set(workflow);
  }

  private void processAfterWithCreate(Workflow<?> workflow, String stepName, Object response) {

    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    if (step.getType().equals(StepType.ACTION)) {
      var transactionId = WorkflowContext.getTransactionId();
      if (transactionId != null) {
        step.setTransactionId(transactionId);
      } else {
        throw new WorkflowException(500, "Can't try transaction id ");
      }
    }
    Map<String, Object> metadata = step.getMetadata();
    var index = JsonPath.parse(metadata.get("histories")).read("$.length()", Integer.class) - 1;
    JsonParser.put(metadata, "histories[" + index + "].response", getBodyFromResponse(response));
    if (stepName.equals(workflowProperties.getOnboarding().getFirst().getStep())) {
      workflow.setTargetId(WorkflowContext.getInitTargetId());
    }
    this.finalProcess(workflow, step.getName());

    workflowService.save(workflow);
  }

  private void processAfterWithApprove(JoinPoint joinPoint, Workflow<?> workflow, Object response) {
    var transactionId = this.getTransactionId(joinPoint);
    var step =
        CollectionUtils.findFirst(
                workflow.getSteps(), s -> transactionId.equals(s.getTransactionId()))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.NOT_FOUND.value(),
                        "Step not found(transactionId:" + transactionId + ")"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    var index = JsonPath.parse(metadata.get("histories")).read("$.length()", Integer.class) - 1;
    JsonParser.put(metadata, "histories[" + index + "].response", getBodyFromResponse(response));
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  private void processAfterWithCancel(Workflow<?> workflow, Object response) {
    var metadata = workflow.getMetadata();
    var histories = metadata.get("histories");
    var index = histories == null ? 0 : JsonPath.parse(histories).read("$.length()", Integer.class);
    JsonParser.put(metadata, "histories[" + index + "].response", getBodyFromResponse(response));
    workflowService.save(workflow);
  }

  private void processAfterThrowWithCreate(Workflow<?> workflow, String stepName, Throwable ex) {
    var step =
        CollectionUtils.findFirst(workflow.getSteps(), s -> s.getName().equals(stepName))
            .orElseThrow(
                () -> new WorkflowException(HttpStatus.NOT_FOUND.value(), "Step not found"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    var index = JsonPath.parse(metadata.get("histories")).read("$.length()", Integer.class) - 1;
    JsonParser.put(metadata, "histories[" + index + "].error", ex.getMessage());
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  private void processAfterThrowWithApprove(
      JoinPoint joinPoint, Workflow<?> workflow, Throwable ex) {
    var transactionId = this.getTransactionId(joinPoint);
    var step =
        CollectionUtils.findFirst(
                workflow.getSteps(), s -> transactionId.equals(s.getTransactionId()))
            .orElseThrow(
                () ->
                    new WorkflowException(
                        HttpStatus.NOT_FOUND.value(),
                        "Step not found(transactionId:" + transactionId + ")"));
    step.setEndTime(ZonedDateTime.now());
    Map<String, Object> metadata = step.getMetadata();
    var index = JsonPath.parse(metadata.get("histories")).read("$.length()", Integer.class) - 1;
    JsonParser.put(metadata, "histories[" + index + "].error", ex.getMessage());
    this.finalProcess(workflow, step.getName());
    workflowService.save(workflow);
  }

  private void processAfterThrowWithCancel(Workflow<?> workflow, Throwable ex) {
    Map<String, Object> metadata = workflow.getMetadata();
    var index = JsonPath.parse(metadata.get("histories")).read("$.length()", Integer.class) - 1;
    JsonParser.put(metadata, "histories[" + index + "].error", ex.getMessage());
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
    throw new WorkflowException(HttpStatus.NOT_FOUND.value(), "Target id not try");
  }

  private Object getBodyFromResponse(Object responseEntity) {
    var result = new HashMap<>();
    var response = ((ResponseEntity<?>) responseEntity).getBody();
    if (response instanceof ApiResponse<?> apiResponse) {
      response = apiResponse.getResult();
    }
    if (response != null) {
      result.put(StringUtils.lowercaseFirstLetter(response.getClass().getSimpleName()), response);
    }
    return result;
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
    throw new WorkflowException(HttpStatus.NOT_FOUND.value(), "Can't try transaction id");
  }

  private void finalProcess(Workflow<?> workflow, String step) {
    workflowService.calculateStatus(workflow, step);
    workflowService.calculatePrevSteps(workflow);
    workflowService.calculateCurrentSteps(workflow);
    workflowService.calculateNextSteps(workflow, step);
  }

  private Object extractRequest(JoinPoint joinPoint) {
    Map<String, Object> argsMap = new HashMap<>();
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] argNames = codeSignature.getParameterNames();
    Object[] argValues = joinPoint.getArgs();
    for (int i = 0; i < argNames.length; i++) {
      var key =
          argValues[i] == null
                  || isPrimitiveOrWrapper(argValues[i].getClass())
                  || argValues[i].getClass().equals(String.class)
                  || argValues[i].getClass().getSimpleName().isEmpty()
              ? argNames[i]
              : StringUtils.lowercaseFirstLetter(argValues[i].getClass().getSimpleName());
      argsMap.put(key, argValues[i]);
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
