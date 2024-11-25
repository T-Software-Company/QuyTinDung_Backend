package com.tsoftware.qtd.model;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Workflow {
  UUID getTargetId();

  void setTargetId(UUID value);

  String getCurrentStep();

  void setCurrentStep(String value);

  String getNextStep();

  void setNextStep(String value);

  WorkflowStatus getStatus();

  void setStatus(WorkflowStatus value);

  ZonedDateTime getStatusUpdatedTime();

  void setStatusUpdatedTime(ZonedDateTime value);

  Map<String, Object> getMetadata();

  void setMetadata(Map<String, Object> value);

  List<StepHistory> getStepHistories();

  void setStepHistories(List<StepHistory> value);
}
