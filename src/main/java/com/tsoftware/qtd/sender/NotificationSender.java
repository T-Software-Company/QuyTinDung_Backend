package com.tsoftware.qtd.sender;

import com.tsoftware.qtd.dto.notification.NotificationResponse;

public interface NotificationSender {
  void send(NotificationResponse notificationResponse);
}
