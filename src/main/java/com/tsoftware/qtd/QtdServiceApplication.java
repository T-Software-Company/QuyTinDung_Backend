package com.tsoftware.qtd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QtdServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(QtdServiceApplication.class, args);
  }
}
