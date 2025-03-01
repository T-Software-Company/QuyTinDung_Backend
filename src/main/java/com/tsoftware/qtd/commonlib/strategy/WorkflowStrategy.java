package com.tsoftware.qtd.commonlib.strategy;

import com.tsoftware.qtd.commonlib.model.Workflow;
import org.aspectj.lang.JoinPoint;

public interface WorkflowStrategy {
  void beforeProcess(JoinPoint joinPoint, String stepName);

  void afterProcess(Workflow<?> workflow, String stepName, Object response);

  void afterThrowProcess(Workflow<?> workflow, String stepName, Throwable ex);
}
