package com.tsoftware.qtd.commonlib.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tsoftware.qtd.commonlib.annotation.*;
import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.factory.WorkflowStrategyFactory;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WorkflowAspect {

  private final WorkflowStrategyFactory workflowStrategyFactory;

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
  public void beforeProceedWorkflow(JoinPoint joinPoint, WorkflowEngine workflowAPI) {
    var stepName = workflowAPI.step();
    var action = workflowAPI.action();
    workflowStrategyFactory.getStrategy(action).beforeProcess(joinPoint, stepName);
  }

  @AfterReturning(
      value = "@annotation(workflowAPI)",
      returning = "response",
      argNames = "joinPoint,response,workflowAPI")
  public void afterReturnWorkflow(
      JoinPoint joinPoint, Object response, WorkflowEngine workflowAPI) {
    var action = workflowAPI.action();
    workflowStrategyFactory.getStrategy(action).afterProcess(response);
  }

  @AfterThrowing(
      value = "@annotation(workflowAPI)",
      throwing = "ex",
      argNames = "joinPoint,ex,workflowAPI")
  public void afterThrowWorkflow(JoinPoint joinPoint, Throwable ex, WorkflowEngine workflowAPI) {
    var action = workflowAPI.action();
    this.workflowStrategyFactory.getStrategy(action).afterThrowProcess(ex);
  }

  @After(value = "@annotation(workflowAPI)")
  public void afterWorkflow() {
    WorkflowContext.clear();
  }
}
