package com.tsoftware.qtd.listener;

import com.tsoftware.qtd.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener {
  private final ApplicationContext applicationContext;

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleNotificationEvent(NotificationEvent event) {
    applicationContext.getBean(event.getType().getHandle()).handle(event);
  }
}
