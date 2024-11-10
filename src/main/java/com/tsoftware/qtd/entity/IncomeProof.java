package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.IncomeProofType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class IncomeProof extends AbstractAuditEntity {
  
  private String link;
  
  @ManyToOne
  private Customer customer;

  @ManyToOne
  private AppraisalPlan appraisalPlan;

  @Enumerated(EnumType.STRING)
  private IncomeProofType incomeProofType;
}
