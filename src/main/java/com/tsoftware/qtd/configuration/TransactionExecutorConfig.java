package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.commonlib.executor.TransactionExecutorRegistry;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionExecutorConfig {
  @Bean
  public ServiceLocatorFactoryBean transactionExecutorRegistry() {
    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
    factoryBean.setServiceLocatorInterface(TransactionExecutorRegistry.class);
    return factoryBean;
  }
}
