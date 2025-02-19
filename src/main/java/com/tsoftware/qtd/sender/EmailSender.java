package com.tsoftware.qtd.sender;

import com.tsoftware.qtd.dto.notification.NotificationResponse;
import com.tsoftware.qtd.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSender implements NotificationSender {
  private final MailService mailService;

  @Override
  public void send(NotificationResponse notificationResponse) {}
}
