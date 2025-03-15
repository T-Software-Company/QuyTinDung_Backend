package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
@SuperBuilder
public class Disbursement extends AbstractAuditEntity {

  private BigDecimal amount;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime disbursementDate;

  private String description;

  @Enumerated(EnumType.ORDINAL)
  private DisbursementStatus status;

  @ManyToOne private LoanAccount loanAccount;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @ManyToOne(fetch = FetchType.LAZY)
  private Application application;
}
