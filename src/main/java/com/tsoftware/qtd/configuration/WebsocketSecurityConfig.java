package com.tsoftware.qtd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
public class WebsocketSecurityConfig {
  @Bean
  public AuthorizationManager<Message<?>> messageAuthorizationManager() {
    var messages =
        MessageMatcherDelegatingAuthorizationManager.builder()
            .simpTypeMatchers(
                SimpMessageType.CONNECT, SimpMessageType.MESSAGE, SimpMessageType.HEARTBEAT)
            .authenticated()
            .simpTypeMatchers(SimpMessageType.DISCONNECT, SimpMessageType.DISCONNECT_ACK)
            .permitAll()
            .nullDestMatcher()
            .authenticated()
            .simpDestMatchers("/app/**")
            .authenticated()
            .simpSubscribeDestMatchers("/topic/**", "user/**")
            .authenticated()
            .anyMessage()
            .authenticated();
    return messages.build();
  }
}
