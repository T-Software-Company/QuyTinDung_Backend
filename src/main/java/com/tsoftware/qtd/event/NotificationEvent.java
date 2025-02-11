package com.tsoftware.qtd.event;

import com.tsoftware.qtd.constants.EnumType.NotificationType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationEvent extends ApplicationEvent {
  private final NotificationType type;
  private final Object object;

  public NotificationEvent(Object source, NotificationType type, Object object) {
    super(source);
    this.type = type;
    this.object = object;
  }
}
