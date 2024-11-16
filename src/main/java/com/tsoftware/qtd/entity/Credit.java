package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class Credit extends AbstractAuditEntity {

  private BigDecimal amount;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime startDate;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime dueDate;

  private BigDecimal interestRate;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.STRING)
  private LoanStatus status;

  @Enumerated(EnumType.STRING)
  private LoanSecurityType loanSecurityType;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private LoanPlan loanPlan;

  @OneToOne(mappedBy = "credit", fetch = FetchType.LAZY)
  private ValuationMeeting valuationMeeting;

  @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private LoanRequest loanRequest;

  @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
  private List<Asset> assets;

  @OneToOne(mappedBy = "credit", fetch = FetchType.LAZY)
  private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotification;

  @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
  private List<AssetRepossessionNotice> assetRepossessionNotices;

  @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
  private List<Disbursement> disbursements;

  @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
  private List<LoanRecordRelate> loanRecordRelates;

  @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
  private List<LoanPurposeDocument> loanPurposeDocuments;

  @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
  private List<LoanCollection> loanCollections;
}
