package com.tsoftware.qtd.model;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnboardingWorkflow implements Workflow {
  private UUID targetId;
  private String currentStep;
  private String nextStep;
  private WorkflowStatus status;
  private ZonedDateTime statusUpdatedTime;
  private Map<String, Object> metadata;
  private List<StepHistory> stepHistories;
}
