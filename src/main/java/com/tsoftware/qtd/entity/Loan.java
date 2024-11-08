package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

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
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_plan_id", nullable = false)
  private LoanPlan loanPlan;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_request_id", nullable = false)
  private LoanRequest loanRequest;

  @OneToOne(mappedBy = "loan")
  private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotification;
 }
