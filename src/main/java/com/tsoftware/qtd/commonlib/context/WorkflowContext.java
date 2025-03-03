package com.tsoftware.qtd.commonlib.context;

import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.UUID;

public final class WorkflowContext {
  private static final ThreadLocal<Workflow<?>> workflow = new ThreadLocal<>();
  private static final ThreadLocal<UUID> transactionId = new ThreadLocal<>();
  private static final ThreadLocal<UUID> initTargetId = new ThreadLocal<>();
  private static final ThreadLocal<Step> step = new ThreadLocal<>();

  public static UUID getTransactionId() {
    return transactionId.get();
  }

  public static void setTransactionId(UUID transactionId) {
    WorkflowContext.transactionId.set(transactionId);
  }

  public static UUID getInitTargetId() {
    return initTargetId.get();
  }

  public static void setInitTargetId(UUID initTargetId) {
    WorkflowContext.initTargetId.set(initTargetId);
  }

  public static void setStep(Step step) {
    WorkflowContext.step.set(step);
  }

  public static Step getStep() {
    return step.get();
  }

  public static Workflow<?> getWorkflow() {
    return workflow.get();
  }

  public static void setWorkflow(Workflow<?> workflow) {
    WorkflowContext.workflow.set(workflow);
  }

  public static void clear() {
    workflow.remove();
    transactionId.remove();
    initTargetId.remove();
  }
}
