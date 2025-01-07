package com.tsoftware.qtd.commonlib.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public abstract class AbstractWorkflowRequest<T> implements WorkflowRequest<T> {
  UUID targetId;
  String currentStep;
  T payload;
}
