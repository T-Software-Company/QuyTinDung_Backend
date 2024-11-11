package com.tsoftware.qtd.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "idp")
public class IdpProperties {

  private String clientId;
  private String clientSecret;
  private String realm;
  private String serverUrl;
}
