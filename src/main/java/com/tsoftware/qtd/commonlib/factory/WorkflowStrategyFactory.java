package com.tsoftware.qtd.commonlib.factory;

import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.strategy.WorkflowStrategy;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkflowStrategyFactory {
  private final Map<WorkflowEngine.WorkflowAction, WorkflowStrategy> strategies;

  public WorkflowStrategy getStrategy(WorkflowEngine.WorkflowAction action) {
    return Optional.ofNullable(strategies.get(action))
        .orElseThrow(
            () ->
                new WorkflowException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Invalid workflow action: " + action));
  }
}
