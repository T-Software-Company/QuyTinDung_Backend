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
  @JoinColumn(name = "loan_request_id") // Cột tham chiếu đến LoanRequest
  private LoanRequest loanRequest;

  @OneToOne private Loan loan;

  @OneToOne private AppraisalPlan appraisalPlan;
}
