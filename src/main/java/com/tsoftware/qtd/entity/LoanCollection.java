package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class LoanCollection extends AbstractAuditEntity {

  private BigDecimal amountDue;
  private BigDecimal amountPaid;
  private BigDecimal remainingAmount;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime dueDate;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime paymentDate;

  private String paymentMethod;
  private String status;
  private String note;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @ManyToOne private Application application;
}
