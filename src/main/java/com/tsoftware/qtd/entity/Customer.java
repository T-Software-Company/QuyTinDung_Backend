package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
@SuperBuilder
public class Customer extends AbstractAuditEntity {

  private String fullName;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private String phone;

  private String note;
  private String signaturePhoto;
  private Gender gender;
  private String status;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<LoanPlan> loanPlans;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Credit> credits;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<LoanRequest> loanRequests;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotifications;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<AssetRepossessionNotice> assetRepossessionNotices;

  @OneToOne(cascade = CascadeType.ALL)
  private PassPort passPort;

  @OneToOne(cascade = CascadeType.ALL)
  private CCCD cccd;

  @OneToOne(cascade = CascadeType.ALL)
  private CMND cmnd;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<IncomeProof> incomeProofs;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<AppraisalReport> appraisalReports;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<AppraisalPlan> appraisalPlans;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Asset> assets;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Disbursement> disbursements;
}
