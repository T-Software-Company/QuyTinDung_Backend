package com.tsoftware.qtd.commonlib.configuration;

import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.constant.StepType;
import com.tsoftware.qtd.commonlib.strategy.*;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.convert.converter.Converter;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Configuration
public class WorkflowAutoConfig {

  @Component
  @ConfigurationPropertiesBinding
  public static class ExpressionStringConverter implements Converter<String, Expression> {
    @Override
    public Expression convert(@NonNull String s) {
      ExpressionParser parser = new SpelExpressionParser();
      return parser.parseExpression(s);
    }
  }

  @Component
  @ConfigurationPropertiesBinding
  public static class ExpressionBooleanConverter implements Converter<Boolean, Expression> {
    @Override
    public Expression convert(Boolean b) {
      ExpressionParser parser = new SpelExpressionParser();
      return parser.parseExpression(b.toString());
    }
  }

  @Component
  @ConfigurationPropertiesBinding
  public static class StepTypeConverter implements Converter<String, StepType> {

    @Override
    public StepType convert(@NonNull String source) {
      if (source.isBlank()) {
        return StepType.ACTION;
      }
      return StepType.valueOf(source.toUpperCase());
    }
  }

  @Bean
  @ConditionalOnMissingBean
  public BeanFactoryResolver beanFactoryResolver(ApplicationContext applicationContext) {
    return new BeanFactoryResolver(applicationContext);
  }

  @Bean
  public Map<WorkflowEngine.WorkflowAction, WorkflowStrategy> workflowStrategies(
      CreateWorkflowStrategy createStrategy,
      ApproveWorkflowStrategy approveStrategy,
      CancelWorkflowStrategy cancelStrategy,
      UpdateWorkflowStrategy updateStrategy) {
    Map<WorkflowEngine.WorkflowAction, WorkflowStrategy> strategies =
        new EnumMap<>(WorkflowEngine.WorkflowAction.class);
    strategies.put(WorkflowEngine.WorkflowAction.CREATE, createStrategy);
    strategies.put(WorkflowEngine.WorkflowAction.APPROVE, approveStrategy);
    strategies.put(WorkflowEngine.WorkflowAction.CANCEL, cancelStrategy);
    strategies.put(WorkflowEngine.WorkflowAction.UPDATE, updateStrategy);
    return strategies;
  }
}
