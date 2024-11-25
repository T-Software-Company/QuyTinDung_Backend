package com.tsoftware.qtd.configuration;

import com.tsoftware.commonlib.model.Workflow;
import com.tsoftware.commonlib.service.WorkflowStorage;
import com.tsoftware.qtd.service.impl.WorkflowServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfig {
  @Bean
  public WorkflowStorage<Workflow> workflowStorage(WorkflowServiceImpl workflowServiceImpl) {
    return (WorkflowStorage) workflowServiceImpl;
  }
}
