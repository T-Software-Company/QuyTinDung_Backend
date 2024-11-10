package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Customer extends AbstractAuditEntity {

  private String fullName;
  private String email;
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

  @OneToOne private PassPort passPort;

  @OneToOne private CCCD cccd;

  @OneToOne private CMND cmnd;

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
