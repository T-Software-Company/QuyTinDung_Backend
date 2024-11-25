package com.tsoftware.qtd.model;

import java.util.UUID;

public interface WorkflowRequest {
  UUID getTargetId();

  void setTargetId(UUID value);

  String getCurrentStep();

  void setCurrentStep(String step);
}
