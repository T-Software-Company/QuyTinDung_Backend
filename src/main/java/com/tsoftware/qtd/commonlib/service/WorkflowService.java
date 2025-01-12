package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.List;
import java.util.UUID;

public interface WorkflowService {
  List<Workflow<?>> getByTargetIdAndStatus(UUID targetId, WorkflowStatus status);

  Workflow<?> getByTransactionId(UUID transactionId);

  Workflow<?> save(Workflow<?> workflow);

  Workflow<?> init(UUID targetId);

  Workflow<?> get(UUID id);

  void validateNextStep(Workflow<?> workflow, String nextStep);

  void calculateStatus(Workflow<?> workflow, String stepName);

  void calculateCurrentSteps(Workflow<?> workflow);

  void calculateNextSteps(Workflow<?> workflow, String stepName);
}
