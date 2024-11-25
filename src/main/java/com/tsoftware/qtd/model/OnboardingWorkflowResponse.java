package com.tsoftware.qtd.model;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OnboardingWorkflowResponse extends AbstractWorkflow {
  Boolean isSuccess;
  Map<String, Object> metadata;
}
