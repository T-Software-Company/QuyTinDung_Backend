package com.tsoftware.qtd.dto.debtNotification;

import com.tsoftware.qtd.constants.EnumType.NotificationStatus;
import com.tsoftware.qtd.constants.EnumType.NotificationType;
import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.entity.Credit;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class DebtNotificationDto {

  @NotNull(message = "ID_REQUIRED")
  Long id;

  @NotNull(message = "CREATED_AT_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime createdAt;

  @NotNull(message = "UPDATED_AT_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime updatedAt;

  String lastModifiedBy;
  String createdBy;

  @NotNull(message = "CUSTOMER_REQUIRED")
  CustomerDto customer;

  @NotNull(message = "LOAN_REQUIRED")
  Credit credit;

  @NotNull(message = "NOTIFICATION_DATE_REQUIRED")
  @Past(message = "NOTIFICATION_DATE_MUST_BE_IN_PAST")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime notificationDate;

  @NotNull(message = "DUE_DATE_REQUIRED")
  @Past(message = "DUE_DATE_MUST_BE_IN_PAST")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime dueDate;

  @NotNull(message = "AMOUNT_DUE_REQUIRED")
  @Positive(message = "AMOUNT_DUE_MUST_BE_POSITIVE")
  BigDecimal amountDue;

  @NotNull(message = "NOTIFICATION_TYPE_REQUIRED")
  NotificationType notificationType;

  @NotNull(message = "NOTIFICATION_STATUS_REQUIRED")
  NotificationStatus notificationStatus;

  @NotBlank(message = "DESCRIPTION_REQUIRED")
  String description;
}
