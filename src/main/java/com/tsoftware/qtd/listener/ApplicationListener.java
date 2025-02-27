package com.tsoftware.qtd.listener;

import com.tsoftware.qtd.constants.EnumType.NotificationType;
import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import com.tsoftware.qtd.dto.notification.CustomerNotificationRequest;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationRequest;
import com.tsoftware.qtd.dto.notification.NotificationRequest;
import com.tsoftware.qtd.dto.notification.NotificationResponse;
import com.tsoftware.qtd.event.*;
import com.tsoftware.qtd.service.*;
import java.util.*;
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
  private final ApplicationEventPublisher applicationEventPublisher;
  private final CustomerNotificationService customerNotificationService;
  private final CustomerService customerService;

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handApprovalSubmittedEvent(ApprovalSubmittedEvent event) {
    var notificationType =
        NotificationType.SubmittedTypeFromProcessType(event.getApprovalProcessResponse().getType());
    var approvalProcessId = event.getApprovalProcessResponse().getId();
    var notification = generateApprovalRequestNotification(approvalProcessId, notificationType);
    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleApprovedEvent(ApprovedEvent event) {
    var approvalProcess = event.getApprovalProcessDTO();

    var application = approvalProcess.getApplication();
    var notificationType = NotificationType.ApprovedTypeFromProcessType(approvalProcess.getType());

    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("applicationId", application.getId());
    notificationMetadata.put("customerId", application.getCustomer().getId());
    notificationMetadata.put("approvalProcessId", approvalProcess.getId());
    notificationMetadata.put("referenceIds", approvalProcess.getReferenceIds());

    var notification = createNotification(notificationType, notificationMetadata);
    createCustomerNotification(
        application.getCustomer().getId(), notification.getId(), notificationType);
    createEmployeeNotifications(approvalProcess, notification.getId(), notificationType);

    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleRejectedEvent(RejectedEvent event) {
    var approvalProcess = event.getApprovalProcessDTO();

    var application = approvalProcess.getApplication();
    var notificationType = NotificationType.RejectedTypeFromProcessType(approvalProcess.getType());

    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("applicationId", application.getId());
    notificationMetadata.put("customerId", application.getCustomer().getId());
    notificationMetadata.put("approvalProcessId", approvalProcess.getId());

    var notification = createNotification(notificationType, notificationMetadata);
    createCustomerNotification(
        application.getCustomer().getId(), notification.getId(), notificationType);
    createEmployeeNotifications(approvalProcess, notification.getId(), notificationType);

    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleValuationMeetingCreatedEvent(ValuationMeetingCreatedEvent event) {
    var valuationMeeting = event.getValuationMeetingResponse();
    var notificationType = NotificationType.CREATE_VALUATION_MEETING;

    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("valuationMeetingId", valuationMeeting.getId());
    notificationMetadata.put("applicationId", valuationMeeting.getApplication().getId());

    var notification = createNotification(notificationType, notificationMetadata);
    valuationMeeting
        .getParticipants()
        .forEach(
            participant -> {
              var employeeNotificationRequest =
                  EmployeeNotificationRequest.builder()
                      .message(notificationType.getContent())
                      .notification(
                          EmployeeNotificationRequest.Notification.builder()
                              .id(notification.getId().toString())
                              .build())
                      .employee(
                          EmployeeNotificationRequest.Employee.builder()
                              .id(participant.getId().toString())
                              .build())
                      .isRead(false)
                      .build();
              employeeNotificationService.create(employeeNotificationRequest);
            });

    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleCreditRatingCreatedEvent(CreditRatingCreatedEvent event) {
    var creditRating = event.getCreditRatingResponse();
    var notificationType = NotificationType.CREATE_CREDIT_RATING;
    var applicationId = creditRating.getApplication().getId();
    var customer = customerService.getByApplicationId(applicationId);

    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("creditRatingId", creditRating.getId());
    notificationMetadata.put("applicationId", applicationId);
    notificationMetadata.put("customerId", customer.getId());

    var notification = createNotification(notificationType, notificationMetadata);

    createCustomerNotification(customer.getId(), notification.getId(), notificationType);

    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleAppraisalPlanCreatedEvent(AppraisalMeetingCreatedEvent event) {
    var appraisalPlan = event.getAppraisalMeetingResponse();
    var notificationType = NotificationType.CREATE_VALUATION_MEETING;

    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("appraisalPlanId", appraisalPlan.getId());
    notificationMetadata.put("applicationId", appraisalPlan.getApplication().getId());

    var notification = createNotification(notificationType, notificationMetadata);

    appraisalPlan
        .getParticipants()
        .forEach(
            participant -> {
              var employeeNotificationRequest =
                  EmployeeNotificationRequest.builder()
                      .message(notificationType.getContent())
                      .notification(
                          EmployeeNotificationRequest.Notification.builder()
                              .id(notification.getId().toString())
                              .build())
                      .employee(
                          EmployeeNotificationRequest.Employee.builder()
                              .id(participant.getId().toString())
                              .build())
                      .isRead(false)
                      .build();
              employeeNotificationService.create(employeeNotificationRequest);
            });

    applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
  }

  private NotificationResponse generateApprovalRequestNotification(
      UUID approvalProcessId, NotificationType type) {
    var approvalProcess = approvalProcessService.getDTOById(approvalProcessId);
    var applicationId = approvalProcess.getApplication().getId();
    var application = applicationService.getById(applicationId);

    var notificationMetadata = new HashMap<String, Object>();
    notificationMetadata.put("applicationId", applicationId);
    notificationMetadata.put("customerId", application.getCustomer().getId());
    notificationMetadata.put("approvalProcessId", approvalProcessId);

    var notification = createNotification(type, notificationMetadata);
    createEmployeeNotifications(approvalProcess, notification.getId(), type);
    return notification;
  }

  private NotificationResponse createNotification(
      NotificationType type, Map<String, Object> metadata) {
    String content = type.getContent();

    var notificationRequest =
        NotificationRequest.builder()
            .content(content)
            .title(type.getTitle())
            .type(type)
            .metadata(metadata)
            .build();
    return notificationService.create(notificationRequest);
  }

  private void createCustomerNotification(
      UUID customerId, UUID notificationId, NotificationType type) {
    if (type.getCustomerMessage() == null) return;

    var customerNotificationRequest =
        CustomerNotificationRequest.builder()
            .customer(
                CustomerNotificationRequest.Customer.builder().id(customerId.toString()).build())
            .notification(
                CustomerNotificationRequest.Notification.builder()
                    .id(notificationId.toString())
                    .build())
            .message(type.getCustomerMessage())
            .isRead(false)
            .build();
    customerNotificationService.create(customerNotificationRequest);
  }

  private void createEmployeeNotifications(
      ApprovalProcessDTO approvalProcess, UUID notificationId, NotificationType type) {
    var employeeIds = this.extractEmployeeIds(approvalProcess);
    employeeIds.stream()
        .distinct()
        .forEach(
            id -> {
              var employeeNotificationRequest =
                  EmployeeNotificationRequest.builder()
                      .employee(
                          EmployeeNotificationRequest.Employee.builder().id(id.toString()).build())
                      .notification(
                          EmployeeNotificationRequest.Notification.builder()
                              .id(notificationId.toString())
                              .build())
                      .message(type.getEmployeeMessage())
                      .isRead(false)
                      .build();
              employeeNotificationService.create(employeeNotificationRequest);
            });
  }

  private List<UUID> extractEmployeeIds(ApprovalProcessDTO approvalProcess) {
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
    return employeeIds;
  }
}
