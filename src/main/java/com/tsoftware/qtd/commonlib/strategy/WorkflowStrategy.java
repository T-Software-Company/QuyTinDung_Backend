package com.tsoftware.qtd.commonlib.strategy;

import org.aspectj.lang.JoinPoint;

public interface WorkflowStrategy {
  void beforeProcess(JoinPoint joinPoint, String stepName);

  void afterProcess(Object response);

  void afterThrowProcess(Throwable ex);
}
