package com.tsoftware.qtd.listener;

import com.tsoftware.qtd.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {
  private final ApplicationContext applicationContext;

  @Async
  @EventListener
  public void handleNotificationEvent(NotificationEvent event) {
    applicationContext
        .getBean(event.getNotificationResponse().getType().getHandler())
        .handle(event);
  }
}
