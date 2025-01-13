package com.tsoftware.qtd.dto.workflow;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingWorkflowDTO implements Workflow<StepHistoryDTO> {
  private UUID id;
  private UUID targetId;
  private List<String> nextSteps;
  private List<String> currentSteps;
  private WorkflowStatus status;
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private String createdBy;
  private Map<String, Object> metadata;
  private List<StepHistoryDTO> steps;
}
