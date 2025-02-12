package com.tsoftware.qtd.listener;

import com.tsoftware.qtd.constants.EnumType.NotificationType;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationRequest;
import com.tsoftware.qtd.dto.notification.NotificationRequest;
import com.tsoftware.qtd.event.LoanRequestSubmittedEvent;
import com.tsoftware.qtd.event.NotificationEvent;
import com.tsoftware.qtd.service.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LoanRequestListener {
  private final ApplicationContext applicationContext;
  private final NotificationService notificationService;
  private final ApprovalProcessService approvalProcessService;
  private final ApplicationService applicationService;
  private final CustomerService customerService;
  private final CustomerNotificationService customerNotificationService;
  private final EmployeeNotificationService employeeNotificationService;

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handLoanRequestSubmittedEvent(LoanRequestSubmittedEvent event) {
    var approvalProcess = event.getApprovalProcessResponse();
    var approvalProcessId = approvalProcess.getId();
    var applicationId = approvalProcess.getApplication().getId();
    var application = applicationService.getById(UUID.fromString(applicationId));
    var customer = customerService.getById(UUID.fromString(application.getCustomer().getId()));
    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("applicationId", applicationId);
    notificationMetadata.put("customerId", customer.getId());
    notificationMetadata.put("approvalProcessId", approvalProcessId);
    var notificationRequest =
        NotificationRequest.builder()
            .content("Một yêu cầu vay mới đã được tạo")
            .title("Tạo yêu cầu vay")
            .type(NotificationType.CREATE_LOAN_REQUEST)
            .metadata(notificationMetadata)
            .build();
    var notification = notificationService.create(notificationRequest);

    var employeeIds = new ArrayList<String>();
    employeeIds.addAll(
        approvalProcess.getApprovals().stream()
            .map(approval -> approval.getApprover().getId())
            .toList());
    employeeIds.addAll(
        approvalProcess.getRoleApprovals().stream()
            .flatMap(r -> r.getCurrentApprovals().stream().map(a -> a.getApprover().getId()))
            .toList());
    employeeIds.stream()
        .distinct()
        .forEach(
            id -> {
              var employeeNotificationRequest =
                  EmployeeNotificationRequest.builder()
                      .isRead(false)
                      .employee(EmployeeNotificationRequest.Employee.builder().id(id).build())
                      .notification(
                          EmployeeNotificationRequest.Notification.builder()
                              .id(notification.getId().toString())
                              .build())
                      .message("Bạn có một yêu cầu xét duyệt")
                      .build();
              employeeNotificationService.create(employeeNotificationRequest);
            });
    applicationContext.publishEvent(new NotificationEvent(this, notification));
  }
}
