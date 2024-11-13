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
  private Integer phone;

  private String note;
  private String signaturePhoto;
  private Gender gender;
  private String status;

  @OneToOne private Address address;

  @OneToMany(mappedBy = "customer")
  private List<LoanPlan> loanPlans;

  @OneToMany(mappedBy = "customer")
  private List<Credit> credits;

  @OneToMany(mappedBy = "customer")
  private List<LoanRequest> loanRequests;

  @OneToMany(mappedBy = "customer")
  private List<DebtNotification> debtNotifications;

  @OneToMany(mappedBy = "customer")
  private List<AssetRepossessionNotice> assetRepossessionNotices;

  @OneToOne(cascade = CascadeType.ALL)
  private PassPort passPort;

  @OneToOne(cascade = CascadeType.ALL)
  private CCCD cccd;

  @OneToOne(cascade = CascadeType.ALL)
  private CMND cmnd;

  @OneToMany(mappedBy = "customer")
  private List<IncomeProof> incomeProofs;

  @OneToMany(mappedBy = "customer")
  private List<AppraisalReport> appraisalReports;

  @OneToMany(mappedBy = "customer")
  private List<AppraisalPlan> appraisalPlans;

  @OneToMany(mappedBy = "customer")
  private List<Asset> assets;

  @OneToMany(mappedBy = "customer")
  private List<Disbursement> disbursements;
}
