package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class Approve extends AbstractAuditEntity {

  @ManyToOne private Employee approver;

  @ManyToOne(fetch = FetchType.LAZY)
  private AppraisalReport appraisalReport;

  @ManyToOne(fetch = FetchType.LAZY)
  private ValuationReport valuationReport;

  @Enumerated(EnumType.ORDINAL)
  private ApproveStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  private TransactionEntity transaction;
}
