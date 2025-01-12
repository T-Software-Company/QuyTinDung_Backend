package com.tsoftware.qtd.commonlib.context;

import com.tsoftware.qtd.commonlib.model.Workflow;

public final class WorkflowContext {
  private static final ThreadLocal<Workflow<?>> context = new ThreadLocal<>();

  public static Workflow<?> get() {
    return context.get();
  }

  public static void set(Workflow<?> workflow) {
    context.set(workflow);
  }

  public static void clear() {
    context.remove();
  }
}
