package com.tsoftware.qtd.commonlib.configuration;

import com.tsoftware.qtd.commonlib.constant.StepType;
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
        return StepType.DEFAULT;
      }
      return StepType.valueOf(source.toUpperCase());
    }
  }

  @Bean
  @ConditionalOnMissingBean
  public BeanFactoryResolver beanFactoryResolver(ApplicationContext applicationContext) {
    return new BeanFactoryResolver(applicationContext);
  }
}
