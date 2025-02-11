package com.tsoftware.qtd.handler;

import com.tsoftware.qtd.event.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateLoanRequestNotificationHandler implements NotificationHandler {
  @Override
  public void handle(NotificationEvent event) {
    log.info("Notification handling");
  }
}
