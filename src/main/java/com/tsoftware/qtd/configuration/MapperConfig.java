package com.tsoftware.qtd.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class MapperConfig {
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    return mapper;
  }
}
