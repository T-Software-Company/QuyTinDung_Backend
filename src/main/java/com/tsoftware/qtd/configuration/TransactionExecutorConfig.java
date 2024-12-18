package com.tsoftware.qtd.configuration;

import com.tsoftware.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import java.util.Properties;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionExecutorConfig {
  @Bean
  public ServiceLocatorFactoryBean transactionExecutorRegistry() {
    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
    factoryBean.setServiceLocatorInterface(TransactionExecutorRegistry.class);
    factoryBean.setServiceMappings(getExecutorMappings());
    return factoryBean;
  }

  private Properties getExecutorMappings() {
    Properties properties = new Properties();
    properties.putAll(TransactionType.executorMap);
    return properties;
  }
}
