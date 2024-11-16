package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
public class DebtNotification extends AbstractAuditEntity {

  private Long amount;
  private String message;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @ManyToOne private Customer customer;

  @ManyToOne private Credit credit;
}
