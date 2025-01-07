package com.tsoftware.qtd.commonlib.properties;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.expression.Expression;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowRuleProperties {
  private Map<String, Map<String, Expression>> rules;
}
