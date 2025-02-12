package com.tsoftware.qtd.dto.notification;

import com.tsoftware.qtd.constants.EnumType.NotificationType;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class NotificationResponse extends AbstractResponse {
  private NotificationType type;
  private String title;
  private String content;
  private Map<String, Object> metadata;
}
