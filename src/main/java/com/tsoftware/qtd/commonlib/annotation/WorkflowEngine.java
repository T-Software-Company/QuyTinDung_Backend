package com.tsoftware.qtd.commonlib.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowEngine {
  String step() default "unknown";

  WorkflowAction action() default WorkflowAction.CREATE;

  @Getter
  @RequiredArgsConstructor
  enum WorkflowAction {
    CREATE("create"),
    UPDATE("update"),
    APPROVE("approve"),
    CANCEL("cancel");
    private final String value;

    public static WorkflowAction fromValue(String value) {
      for (WorkflowAction action : WorkflowAction.values()) {
        if (action.value.equals(value)) {
          return action;
        }
      }
      return null;
    }
  }
}
