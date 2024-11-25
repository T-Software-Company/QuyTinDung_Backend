package com.tsoftware.qtd.model;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OnboardingWorkflowRequest extends AbstractWorkflow {
  Map<String, Object> metadata;
}
