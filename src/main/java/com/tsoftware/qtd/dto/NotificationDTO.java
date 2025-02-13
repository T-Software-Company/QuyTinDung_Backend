package com.tsoftware.qtd.dto;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NotificationDTO {
  private UUID id;
  // Add other fields based on Notification entity
}
