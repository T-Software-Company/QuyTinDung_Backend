package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.UUID;

public interface WorkflowService {
  Workflow<?> getByTargetId(UUID targetId);

  Workflow<?> getByTransactionId(UUID transactionId);

  Workflow<?> save(Workflow<?> workflow);

  Workflow<?> init();

  <T extends Step> T initStep(String stepName);

  Workflow<?> get(UUID id);

  void validateStep(Workflow<?> workflow, String nextStep);

  void calculateStatus(Workflow<?> workflow, String stepName);

  void calculatePrevSteps(Workflow<?> workflow);

  void calculateCurrentSteps(Workflow<?> workflow);

  void calculateNextSteps(Workflow<?> workflow, String stepName);
}
