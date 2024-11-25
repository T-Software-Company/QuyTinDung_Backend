package com.tsoftware.qtd.model;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import java.util.UUID;

public interface WorkflowResponse {
  UUID getTargetId();

  void setTargetId(UUID value);

  String getCurrentStep();

  void setCurrentStep(String value);

  String getNextStep();

  void setNextStep(String value);

  WorkflowStatus getStatus();

  void setStatus(WorkflowStatus value);
}
