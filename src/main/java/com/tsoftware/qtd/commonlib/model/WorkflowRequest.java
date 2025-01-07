package com.tsoftware.qtd.commonlib.model;

import java.util.UUID;

public interface WorkflowRequest<T> {
  UUID getTargetId();

  void setTargetId(UUID value);

  String getCurrentStep();

  void setCurrentStep(String step);

  T getPayload();

  void setPayload(T payload);
}
