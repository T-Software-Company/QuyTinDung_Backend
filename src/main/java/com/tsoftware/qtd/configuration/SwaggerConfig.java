package com.tsoftware.qtd.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API document", version = "1.0"))
@Slf4j
public class SwaggerConfig {
  @Autowired IdpProperties idpProperties;

  @Bean
  public OpenAPI customerOpenAPI() {
    String tokenUrl =
        idpProperties.getServerUrl()
            + "/realms/"
            + idpProperties.getRealm()
            + "/protocol/openid-connect/token";
    String authorizationUrl =
        idpProperties.getServerUrl()
            + "/realms/"
            + idpProperties.getRealm()
            + "/protocol/openid-connect/auth";

    OAuthFlow oauthFlow = new OAuthFlow().authorizationUrl(authorizationUrl).tokenUrl(tokenUrl);
    OAuthFlows oauthFlows = new OAuthFlows().authorizationCode(oauthFlow);
    SecurityScheme securityScheme =
        new SecurityScheme()
            .type(SecurityScheme.Type.OAUTH2)
            .in(SecurityScheme.In.HEADER)
            .name("Authorization")
            .flows(oauthFlows);
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("keycloakAuth");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("keycloakAuth", securityScheme))
        .addSecurityItem(securityRequirement);
  }
}
