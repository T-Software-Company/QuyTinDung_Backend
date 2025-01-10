package com.tsoftware.qtd.commonlib.model;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Step {
  private String name;
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private List<String> nextSteps;
  private WorkflowStatus status;
  private String transactionId;
  private Map<String, Object> metadata;
}
