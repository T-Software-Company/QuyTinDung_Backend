package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ApplicationStep;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class Application extends AbstractAuditEntity {

  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private ApplicationStep step;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<TransactionEntity> transactions;

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

  @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
  private LoanPlan loanPlan;

  @OneToOne(mappedBy = "application")
  private ValuationMeeting valuationMeeting;

  @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
  private LoanRequest loanRequest;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<Asset> assets;

  @OneToOne(mappedBy = "application")
  private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotification;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<AssetRepossessionNotice> assetRepossessionNotices;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<Disbursement> disbursements;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<LoanRecordRelate> loanRecordRelates;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<LoanPurposeDocument> loanPurposeDocuments;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<LoanCollection> loanCollections;
}
