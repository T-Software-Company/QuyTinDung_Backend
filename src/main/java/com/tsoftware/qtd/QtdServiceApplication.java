package com.tsoftware.qtd;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition
public class QtdServiceApplication {
  private static final Logger log = LoggerFactory.getLogger(QtdServiceApplication.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(QtdServiceApplication.class, args).getEnvironment();
    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
