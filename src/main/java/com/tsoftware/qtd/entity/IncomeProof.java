package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.IncomeProofType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class IncomeProof extends AbstractAuditEntity {

  private String link;

  @ManyToOne private Customer customer;

  @Enumerated(EnumType.STRING)
  private IncomeProofType incomeProofType;

  @ManyToOne private AppraisalPlan appraisalPlan;

  @ManyToOne private AppraisalReport appraisalReport;
}
