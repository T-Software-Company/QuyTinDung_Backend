package com.tsoftware.qtd.initdb;

import com.tsoftware.qtd.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitDatabase implements CommandLineRunner {
  private final initDbService initDbservice;

  @Override
  public void run(String... args) throws Exception {
    initDbservice.createRoles();
    initDbservice.createGroups();
    initDbservice.createAdmin();
    initDbservice.createEmployees();
    initDbservice.createCustomers();
    initDbservice.createApproveSetting();
  }
}
