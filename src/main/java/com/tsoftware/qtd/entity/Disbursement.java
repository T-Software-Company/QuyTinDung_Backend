package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
}
