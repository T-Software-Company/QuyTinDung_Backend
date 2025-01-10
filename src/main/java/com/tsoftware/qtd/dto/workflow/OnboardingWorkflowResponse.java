package com.tsoftware.qtd.dto.workflow;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
public class OnboardingWorkflowResponse extends AbstractResponse {
  private UUID targetId;
  private String nextStep;
  private WorkflowStatus workflowStatus;
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private String createdBy;
  private Map<String, Object> metadata;
  private List<StepHistoryDTO> steps;
}
