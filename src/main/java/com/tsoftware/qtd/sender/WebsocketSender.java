package com.tsoftware.qtd.sender;

import com.tsoftware.qtd.dto.notification.NotificationResponse;
import com.tsoftware.qtd.service.CustomerNotificationService;
import com.tsoftware.qtd.service.EmployeeNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketSender implements NotificationSender {

  private final EmployeeNotificationService employeeNotificationService;
  private final CustomerNotificationService customerNotificationService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @Override
  public void send(NotificationResponse notificationResponse) {
    var employeeNotifications =
        employeeNotificationService.getByNotificationId(notificationResponse.getId());
    var customerNotifications =
        customerNotificationService.getByNotificationId(notificationResponse.getId());
    employeeNotifications.forEach(
        e -> {
          simpMessagingTemplate.convertAndSendToUser(
              e.getEmployee().getUserId(), "/queue/notification", e);
        });
    customerNotifications.forEach(
        c ->
            simpMessagingTemplate.convertAndSendToUser(
                c.getCustomer().getUserId(), "queue/notification", c));
  }
}
