package com.tsoftware.qtd.dto.notification;

import com.tsoftware.qtd.constants.EnumType.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NotificationRequest {
  @NotNull private NotificationType type;

  @NotBlank private String title;

  @NotBlank private String content;

  private Map<String, Object> metadata;
}
