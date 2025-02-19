package com.tsoftware.qtd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocketSecurity
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    registry.enableSimpleBroker("/topic");
    registry.setUserDestinationPrefix("/users");
  }

  @Bean
  public AuthorizationManager<Message<?>> messageAuthorizationManager() {
    var messages =
        MessageMatcherDelegatingAuthorizationManager.builder()
            .nullDestMatcher()
            .authenticated()
            .simpTypeMatchers(
                SimpMessageType.CONNECT,
                SimpMessageType.HEARTBEAT,
                SimpMessageType.UNSUBSCRIBE,
                SimpMessageType.DISCONNECT)
            .permitAll()
            .simpDestMatchers("/app/**", "/users/**")
            .authenticated()
            .simpSubscribeDestMatchers("/topic/**", "users/**")
            .authenticated()
            .anyMessage()
            .authenticated();

    return messages.build();
  }
}
