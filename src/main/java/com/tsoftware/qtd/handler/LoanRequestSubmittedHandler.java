package com.tsoftware.qtd.handler;

import com.tsoftware.qtd.event.NotificationEvent;
import com.tsoftware.qtd.sender.NotificationSender;
import com.tsoftware.qtd.service.EmployeeNotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanRequestSubmittedHandler implements NotificationHandler {
  private final EmployeeNotificationService employeeNotificationService;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final List<NotificationSender> senders;

  @Override
  public void handle(NotificationEvent event) {
    var notification = event.getNotificationResponse();
    senders.forEach(s -> s.send(notification));
  }
}
