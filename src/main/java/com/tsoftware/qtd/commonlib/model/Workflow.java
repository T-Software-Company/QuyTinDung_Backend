package com.tsoftware.qtd.commonlib.model;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Workflow<T extends Step> {
  UUID getId();

  void setId(UUID value);

  UUID getTargetId();

  void setTargetId(UUID value);

  List<String> getPrevSteps();

  void setPrevSteps(List<String> value);

  List<String> getCurrentSteps();

  void setCurrentSteps(List<String> value);

  List<String> getNextSteps();

  void setNextSteps(List<String> value);

  WorkflowStatus getStatus();

  void setStatus(WorkflowStatus value);

  ZonedDateTime getStartTime();

  void setStartTime(ZonedDateTime value);

  ZonedDateTime getEndTime();

  void setEndTime(ZonedDateTime value);

  String getCreatedBy();

  void setCreatedBy(String value);

  Map<String, Object> getMetadata();

  void setMetadata(Map<String, Object> value);

  List<T> getSteps();

  void setSteps(List<T> value);
}
