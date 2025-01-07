package com.tsoftware.qtd.commonlib.model;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StepHistory {
  String step;
  Date execTime;
  String nextStep;
  WorkflowStatus status;
  ZonedDateTime statusUpdatedTime;
}
