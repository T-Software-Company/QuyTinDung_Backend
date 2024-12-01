package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.TransactionStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@EqualsAndHashCode(callSuper = true)
public class TransactionEntity extends AbstractAuditEntity {
  @Enumerated(EnumType.ORDINAL)
  private TransactionStatus status;

  @Enumerated(EnumType.ORDINAL)
  private TransactionType type;

  private ZonedDateTime createdAt;
  private ZonedDateTime approvedAt;
  private String approvedBy;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Object metadata;
}
