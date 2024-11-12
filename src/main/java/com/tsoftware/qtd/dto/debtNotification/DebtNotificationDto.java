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


  Long id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  Long amount;
  String message;
}
