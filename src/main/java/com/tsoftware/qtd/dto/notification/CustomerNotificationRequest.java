package com.tsoftware.qtd.dto.notification;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerNotificationRequest {
  @NotNull private Notification notification;
  @NotNull private Customer customer;
  @NotNull private Boolean isRead;
  @NotNull private String message;

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Notification {
    private String id;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Customer {
    private String id;
  }
}
