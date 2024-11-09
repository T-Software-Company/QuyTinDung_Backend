package com.tsoftware.qtd.dto.debtNotification;

import com.tsoftware.qtd.constants.EnumType.NotificationStatus;
import com.tsoftware.qtd.constants.EnumType.NotificationType;
import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.entity.Loan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

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
    Loan loan;

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
    @Size(max = 255, message = "DESCRIPTION_TOO_LONG")
    String description;
}
