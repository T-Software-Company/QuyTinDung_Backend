package com.tsoftware.qtd.dto.notification;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.customer.CustomerSimpleResponse;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class CustomerNotificationResponse extends AbstractResponse {
  private NotificationResponse notification;
  private Boolean isRead;
  private ZonedDateTime readAt;
  private String message;
  private CustomerSimpleResponse customer;
}
