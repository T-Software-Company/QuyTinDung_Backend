package com.tsoftware.qtd.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API document", version = "1.0"))
@Slf4j
public class SwaggerConfig {
	@Value("${idp.url}")
	private String identityProviderURL;
	
	@Value("${idp.realm}")
	private String identityProviderRealm;

	
	@Bean
	public OpenAPI customerOpenAPI() {
		String tokenUrl =
						identityProviderURL + "/realms/" + identityProviderRealm + "/protocol/openid-connect/token";
		String authorizationUrl = identityProviderURL + "/realms/" + identityProviderRealm + "/protocol/openid-connect/auth";
		Scopes scopes =
						new Scopes()
										.addString("openid", "Access user data")
										.addString("profile", "Access user profile information")
										.addString("email", "Access user email information");
		
		OAuthFlow oauthFlow = new OAuthFlow().authorizationUrl(authorizationUrl).tokenUrl(tokenUrl).scopes(scopes);
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
