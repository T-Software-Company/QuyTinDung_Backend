package com.tsoftware.qtd.commonlib.context;

import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.UUID;

public final class WorkflowContext {
  private static final ThreadLocal<Workflow<?>> context = new ThreadLocal<>();

  private static final ThreadLocal<UUID> transactionId = new ThreadLocal<>();

  public static UUID getTransactionId() {
    return transactionId.get();
  }

  public static void setTransactionId(UUID transactionId) {
    WorkflowContext.transactionId.set(transactionId);
  }

  public static Workflow<?> getWorkflow() {
    return context.get();
  }

  public static void set(Workflow<?> workflow) {
    context.set(workflow);
  }

  public static void clear() {
    context.remove();
    transactionId.remove();
  }
}
