package com.tsoftware.qtd.handler;

import com.tsoftware.qtd.event.NotificationEvent;

public interface NotificationHandler {
  void handle(NotificationEvent event);
}
