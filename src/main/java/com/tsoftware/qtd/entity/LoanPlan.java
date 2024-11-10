package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class LoanPlan extends AbstractAuditEntity {

  private BigDecimal totalCapitalRequirement;
  private BigDecimal ownCapital;
  private BigDecimal proposedLoanAmount;
  private BigDecimal income;
  private String repaymentPlan;
  private String note;

  @ManyToOne private Customer customer;

  @ManyToOne private Credit credit;
}
