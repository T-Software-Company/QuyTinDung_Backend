package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_plan")
public class LoanPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long loanPlanId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customerId", nullable = false)
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loanId", nullable = false)
  private Loan loan;

  @OneToOne private LoanRequest loanRequest;

  @OneToOne(mappedBy = "loanPlan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private PurposeLoanRelated purposeLoanRelated;
}
