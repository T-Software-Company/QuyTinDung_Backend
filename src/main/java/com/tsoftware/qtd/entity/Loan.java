package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long loanId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer; // Trỏ vào đối tượng Customer

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_plan_id", nullable = false)
  private LoanPlan loanPlan; // Trỏ vào đối tượng LoanPlan

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_request_id", nullable = false)
  private LoanRequest loanRequest;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "appraisal_plan_id")
  private AppraisalPlan appraisalPlan; // Trỏ vào đối tượng AppraisalPlan
}
