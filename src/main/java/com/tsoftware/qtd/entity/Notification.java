package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.NotificationType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class Notification extends AbstractAuditEntity {
  private NotificationType type;
  private String title;
  private String content;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToMany(mappedBy = "notification")
  private List<CustomerNotification> customerNotifications;

  @OneToMany(mappedBy = "notification")
  private List<EmployeeNotification> employeeNotifications;
}
