package com.tsoftware.qtd.dto.notification;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeNotificationRequest {
  @NotNull private Notification notification;
  @NotNull private Employee employee;
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
  public static class Employee {
    private String id;
  }
}
