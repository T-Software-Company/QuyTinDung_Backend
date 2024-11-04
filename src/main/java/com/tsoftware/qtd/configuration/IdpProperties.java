package com.tsoftware.qtd.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "idp")
public class IdpProperties {

    private String clientId;
    private String clientSecret;
    private String realm;
}
