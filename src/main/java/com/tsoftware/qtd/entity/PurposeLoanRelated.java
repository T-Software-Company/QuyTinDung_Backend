package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purpose_loan_related")
public class PurposeLoanRelated {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long purposeLoanRelatedId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loanRequestId")
  private LoanRequest loanRequest;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loanID")
  private Loan loan;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "appraisalPlanId")
  private AppraisalPlan appraisalPlan;
}
