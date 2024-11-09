package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.util.List;
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

  @ManyToOne
  private Customer customer;

  @OneToMany private List<LoanPlan> loanPlan;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_request_id", nullable = false)
  private LoanRequest loanRequest;

  @OneToMany(mappedBy = "loan")
  private List<Asset> assets;

  @OneToOne(mappedBy = "loan")
  private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotification;
  
  @OneToMany(mappedBy = "loan")
  private List<AssetRepossessionNotice> assetRepossessionNotices;
  
  @OneToMany(mappedBy = "loan")
  private List<Disbursement> disbursements;
}
