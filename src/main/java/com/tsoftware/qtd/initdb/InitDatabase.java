package com.tsoftware.qtd.initdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitDatabase implements CommandLineRunner {
  private final initDbService initDbService;

  @Override
  public void run(String... args) throws Exception {
    initDbService.createRoles();
    initDbService.createGroups();
    initDbService.createAdmin();
    initDbService.createEmployees();
    initDbService.createCustomers();
    initDbService.createApproveSetting();
    initDbService.createInterestSetting();
    initDbService.createRatingCriteria();
  }
}
