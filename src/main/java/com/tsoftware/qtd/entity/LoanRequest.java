package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanCollateralType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "loan_request")
public class LoanRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long loanRequestId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_id", nullable = false)
  private Loan loan;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @OneToOne
  @JoinColumn(name = "loan_plan_id", referencedColumnName = "loanPlanId")
  private LoanPlan loanPlan;

  @OneToMany(mappedBy = "loanRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PurposeLoanRelated> purposeLoanRelated;

  @Column(nullable = false)
  private BigDecimal amount;

  private String purpose;

  @Enumerated(EnumType.STRING)
  private BorrowerType borrowerType;

  @Temporal(TemporalType.DATE)
  private Date loanTerm;

  @Enumerated(EnumType.STRING)
  private LoanCollateralType loanCollateralType;
}
