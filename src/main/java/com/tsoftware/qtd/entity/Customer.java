package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
  private List<Application> applications;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<LoanRequest> loanRequests;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotifications;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<AssetRepossessionNotice> assetRepossessionNotices;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<RelationCustomer> relationCustomers;

  @OneToOne(cascade = CascadeType.ALL)
  private IdentityInfo identityInfo;

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
