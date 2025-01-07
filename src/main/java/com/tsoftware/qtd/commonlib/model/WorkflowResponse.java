package com.tsoftware.qtd.commonlib.model;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import java.util.UUID;

public interface WorkflowResponse<T> {
  UUID getTargetId();

  void setTargetId(UUID value);

  String getCurrentStep();

  void setCurrentStep(String value);

  String getNextStep();

  void setNextStep(String value);

  WorkflowStatus getWorkflowStatus();

  void setWorkflowStatus(WorkflowStatus value);

  T getData();

  void setData(T value);
}
