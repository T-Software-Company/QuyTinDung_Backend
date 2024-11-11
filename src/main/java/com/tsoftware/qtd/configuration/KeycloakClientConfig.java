package com.tsoftware.qtd.configuration;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakClientConfig {
  private final IdpProperties idpProperties;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .clientId(idpProperties.getClientId())
        .clientSecret(idpProperties.getClientSecret())
        .serverUrl(idpProperties.getServerUrl())
        .realm(idpProperties.getRealm())
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .build();
  }
}
