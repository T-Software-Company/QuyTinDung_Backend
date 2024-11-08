package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customerId;

  private String fullName;
  private String email;
  private Integer phone;
  private String note;
  private String signaturePhoto;
  private Gender gender;
  private String status;

  @OneToOne private Address address;

  @ManyToOne private LoanPlan loanPlan;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Loan> loan;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<LoanRequest> loanRequests;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotifications;

//
//      @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//      private List<AssetRepossesionzNotices> assetRepossesionzNotices;

      @OneToOne private PassPort passPort;

      @OneToOne private CCCD cccd;

      @OneToOne private CMND cmnd;

      @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
      private List<IncomeProof> incomeProof;

      @OneToOne private AppraisalReport appraisalReport;

      @OneToOne
      private AppraisalPlan appraisalPlan;

      @OneToMany
      private List<Asset> asset;

}
