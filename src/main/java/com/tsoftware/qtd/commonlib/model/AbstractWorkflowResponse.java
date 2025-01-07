package com.tsoftware.qtd.commonlib.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractWorkflowResponse<T> implements WorkflowResponse<T> {
  UUID targetId;
  String currentStep;
  String nextStep;
  WorkflowStatus workflowStatus;
  T data;
}
