package com.tsoftware.qtd.dto.notification;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class EmployeeNotificationResponse extends AbstractResponse {
  private NotificationResponse notification;
  private Boolean isRead;
  private String message;
  private ZonedDateTime readAt;
}
