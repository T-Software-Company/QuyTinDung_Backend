package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.notification.NotificationResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationEvent extends ApplicationEvent {
  private final NotificationResponse notificationResponse;

  public NotificationEvent(Object source, NotificationResponse notificationResponse) {
    super(source);
    this.notificationResponse = notificationResponse;
  }
}
