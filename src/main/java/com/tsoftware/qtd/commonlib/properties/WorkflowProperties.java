package com.tsoftware.qtd.commonlib.properties;

import com.tsoftware.qtd.commonlib.constant.StepType;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "workflow.rules")
public class WorkflowProperties {

  private List<WorkflowDefinition> onboarding;

  @Getter
  @Setter
  public static class WorkflowDefinition {
    private String step;
    private Expression extractStatus;
    private StepType type;
    private List<String> dependencies = new ArrayList<>();
    private List<NextStepRule> nextStepRules = new ArrayList<>();
  }

  @Getter
  @Setter
  public static class NextStepRule {
    private String step;
    private Expression condition;
  }
}
