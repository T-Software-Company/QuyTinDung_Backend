package com.tsoftware.qtd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
  @Bean
  public WebMvcConfigurer corsConfigure() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(
                "http://localhost:3001",
                "http://localhost:3000",
                "https://tsofware.store",
                "https://localhost:8080",
                "http://localhost:5173")
            .allowedHeaders("*")
            .allowCredentials(true);
      }
    };
  }
}
