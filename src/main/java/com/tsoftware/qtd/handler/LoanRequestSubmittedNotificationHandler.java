package com.tsoftware.qtd.handler;

import com.tsoftware.qtd.event.NotificationEvent;
import com.tsoftware.qtd.service.CustomerNotificationService;
import com.tsoftware.qtd.service.EmployeeNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanRequestSubmittedNotificationHandler implements NotificationHandler {
  private final EmployeeNotificationService employeeNotificationService;
  private final CustomerNotificationService customerNotificationService;

  @Override
  public void handle(NotificationEvent event) {
    var notification = event.getNotificationResponse();
    var notificationId = notification.getId();
    var employeeNotifications = employeeNotificationService.getByNotificationId(notificationId);
    var customerNotifications = customerNotificationService.getByNotificationId(notificationId);

    log.info("Notification handling");
  }
}
