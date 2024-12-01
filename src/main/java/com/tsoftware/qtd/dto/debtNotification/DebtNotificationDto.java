package com.tsoftware.qtd.dto.debtNotification;

import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DebtNotificationDto {

  private UUID id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  Long amount;
  String message;
}
