package com.tsoftware.qtd.listener;

import com.tsoftware.qtd.constants.EnumType.NotificationType;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationRequest;
import com.tsoftware.qtd.dto.notification.NotificationRequest;
import com.tsoftware.qtd.dto.notification.NotificationResponse;
import com.tsoftware.qtd.event.*;
import com.tsoftware.qtd.service.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationListener {
  private final NotificationService notificationService;
  private final ApplicationService applicationService;
  private final EmployeeNotificationService employeeNotificationService;
  private final ApprovalProcessService approvalProcessService;
  private ApplicationEventPublisher applicationEventPublisher;

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handLoanRequestSubmittedEvent(LoanRequestSubmittedEvent event) {
    var content = "Một yêu cầu vay mới đã được tạo";
    var title = " Tạo yêu cầu vay";
    var approvalProcessId = event.getApprovalProcessResponse().getId();
    var notification =
        generateNotificationFormApprovalProcess(
            approvalProcessId, NotificationType.CREATE_LOAN_REQUEST, content, title);
    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handLoanPlanSubmittedEvent(LoanPlanSubmittedEvent event) {
    var content = "Một kế hoạch vay mới đã được tạo";
    var title = "Tạo kế hoạch vay";
    var approvalProcessId = event.getApprovalProcessResponse().getId();
    var notification =
        generateNotificationFormApprovalProcess(
            approvalProcessId, NotificationType.CREATE_LOAN_PLAN, content, title);
    log.info("handling loan plan submitted");
    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handFinancialInfoSubmittedEvent(FinancialInfoSubmittedEvent event) {
    var content = "Thông tin tài chính đã được thêm";
    var title = "Thêm thông tin tài chính";
    var approvalProcessId = event.getApprovalProcessResponse().getId();
    var notification =
        generateNotificationFormApprovalProcess(
            approvalProcessId, NotificationType.CREATE_FINANCIAL_INFO, content, title);
    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handAssetsSubmittedEvent(AssetSubmittedEvent event) {
    var content = "Thông tin tài sản đã được thêm";
    var title = "Thêm thông tin tài sản";
    var approvalProcessId = event.getApprovalProcessResponse().getId();
    var notification =
        generateNotificationFormApprovalProcess(
            approvalProcessId, NotificationType.CREATE_ASSETS, content, title);
    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleValuationMeetingCreatedEvent(ValuationMeetingCreatedEvent event) {
    var content = "Cuộc họp định giá tài sản đã được tạo";
    var title = "Tạo cuộc họp định giá";
    var notificationRequest =
        NotificationRequest.builder()
            .content(content)
            .title(title)
            .type(NotificationType.CREATE_VALUATION_MEETING)
            .build();
    var notification = notificationService.create(notificationRequest);
    event
        .getValuationMeetingResponse()
        .getParticipants()
        .forEach(
            e -> {
              var employeeNotificationRequest =
                  EmployeeNotificationRequest.builder()
                      .message("Bạn có một cuộc họp định giá tài sản")
                      .notification(
                          EmployeeNotificationRequest.Notification.builder()
                              .id(notification.getId().toString())
                              .build())
                      .employee(
                          EmployeeNotificationRequest.Employee.builder()
                              .id(e.getId().toString())
                              .build())
                      .isRead(false)
                      .build();
              employeeNotificationService.create(employeeNotificationRequest);
            });
    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  private NotificationResponse generateNotificationFormApprovalProcess(
      UUID approvalProcessId, NotificationType type, String content, String title) {
    var approvalProcess = approvalProcessService.getDTOById(approvalProcessId);
    var applicationId = approvalProcess.getApplication().getId();
    var application = applicationService.getById(applicationId);
    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("applicationId", applicationId);
    notificationMetadata.put("customerId", application.getCustomer().getId());
    notificationMetadata.put("approvalProcessId", approvalProcessId);
    var notificationRequest =
        NotificationRequest.builder()
            .content(content)
            .title(title)
            .type(type)
            .metadata(notificationMetadata)
            .build();
    var notification = notificationService.create(notificationRequest);

    var employeeIds = new ArrayList<UUID>();
    employeeIds.addAll(
        Optional.ofNullable(approvalProcess.getApprovals()).orElse(new ArrayList<>()).stream()
            .map(approval -> approval.getApprover().getId())
            .toList());
    employeeIds.addAll(
        Optional.ofNullable(approvalProcess.getRoleApprovals()).orElse(new ArrayList<>()).stream()
            .flatMap(
                r ->
                    Optional.ofNullable(r.getCurrentApprovals()).orElse(new ArrayList<>()).stream()
                        .map(a -> a.getApprover().getId()))
            .toList());
    employeeIds.addAll(
        Optional.ofNullable(approvalProcess.getGroupApprovals()).orElse(new ArrayList<>()).stream()
            .flatMap(
                g ->
                    Optional.ofNullable(g.getCurrentApprovals()).orElse(new ArrayList<>()).stream()
                        .map(a -> a.getApprover().getId()))
            .toList());
    employeeIds.stream()
        .distinct()
        .forEach(
            id -> {
              var employeeNotificationRequest =
                  EmployeeNotificationRequest.builder()
                      .isRead(false)
                      .employee(
                          EmployeeNotificationRequest.Employee.builder().id(id.toString()).build())
                      .notification(
                          EmployeeNotificationRequest.Notification.builder()
                              .id(notification.getId().toString())
                              .build())
                      .message("Bạn có một yêu cầu xét duyệt")
                      .build();
              employeeNotificationService.create(employeeNotificationRequest);
            });
    return notification;
  }
}
