package com.tsoftware.qtd.model;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractWorkflow implements WorkflowResponse, WorkflowRequest {
  UUID targetId;
  String currentStep;
  String nextStep;
  WorkflowStatus status;
}
