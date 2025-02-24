package com.tsoftware.qtd.sender;

import com.tsoftware.qtd.dto.notification.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class pushSender implements NotificationSender {

  @Override
  public void send(NotificationResponse notificationResponse) {}
}
