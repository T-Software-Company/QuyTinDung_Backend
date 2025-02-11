package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.Step;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.UUID;
import org.springframework.http.HttpStatus;

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

  default void validateWorkflow(Workflow<?> workflow) {
    if (WorkflowStatus.CANCELLED.equals(workflow.getStatus())) {
      throw new WorkflowException(HttpStatus.BAD_REQUEST.value(), "Workflow has been cancelled");
    }
  }
}
