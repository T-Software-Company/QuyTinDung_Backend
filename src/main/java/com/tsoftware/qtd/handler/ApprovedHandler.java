package com.tsoftware.qtd.handler;

import com.tsoftware.qtd.event.NotificationEvent;
import com.tsoftware.qtd.sender.NotificationSender;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovedHandler implements NotificationHandler {
  private final Map<String, NotificationSender> senders;

  @Override
  public void handle(NotificationEvent event) {
    var notification = event.getNotificationResponse();
    senders.get("websocketSender").send(notification);
    senders.get("emailSender").send(notification);
    senders.get("pushSender").send(notification);
  }
}
