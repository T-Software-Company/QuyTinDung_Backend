package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.List;
import java.util.UUID;

public interface WorkflowService {
  Workflow<?> getByTargetIdAndStatus(UUID targetId, WorkflowStatus status);

  Workflow<?> getByTransactionId(UUID transactionId);

  Workflow<?> save(Workflow<?> workflow);

  Workflow<?> init(UUID targetId);

  Workflow<?> get(UUID id);

  void validateStep(Workflow<?> workflow, String step);

  List<String> calculateNextSteps(Step step);
}
