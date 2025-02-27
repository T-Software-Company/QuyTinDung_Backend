package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.service.FileStorageService;
import com.tsoftware.qtd.service.GoogleCloudStorageService;
import com.tsoftware.qtd.service.LocalStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class StorageConfig {

  @Value("${app.file-storage.active}")
  private String activeStorage;

  @Primary
  @Bean
  public FileStorageService fileStorageService(
      GoogleCloudStorageService googleCloudStorage, LocalStorageService localStorage) {
    return "googleCloudStorage".equals(activeStorage) ? googleCloudStorage : localStorage;
  }
}
