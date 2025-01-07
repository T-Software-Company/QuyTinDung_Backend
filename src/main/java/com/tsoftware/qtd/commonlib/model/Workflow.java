package com.tsoftware.qtd.commonlib.model;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
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

  WorkflowStatus getWorkflowStatus();

  void setWorkflowStatus(WorkflowStatus value);

  ZonedDateTime getStatusUpdatedTime();

  void setStatusUpdatedTime(ZonedDateTime value);

  Map<String, Object> getMetadata();

  void setMetadata(Map<String, Object> value);

  List<StepHistory> getStepHistories();

  void setStepHistories(List<StepHistory> value);
}
