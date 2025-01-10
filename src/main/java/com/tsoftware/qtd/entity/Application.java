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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

  @Enumerated(EnumType.ORDINAL)
  private ApplicationStep step; // ?

  @ManyToMany(mappedBy = "applicationsAssigned", fetch = FetchType.LAZY)
  private List<Employee> LoanProcessor;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<WorkflowTransaction> transactions;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime startDate;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime dueDate;

  private BigDecimal interestRate;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.ORDINAL)
  private LoanStatus status;

  @Enumerated(EnumType.ORDINAL)
  private LoanSecurityType loanSecurityType;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
  private LoanPlan loanPlan;

  @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
  private FinancialInfo financialInfo;

  @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
  private LoanAccount loanAccount;

  @OneToOne(mappedBy = "application")
  private ValuationMeeting valuationMeeting;

  @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
  private LoanRequest loanRequest;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<Asset> assets;

  @OneToOne(mappedBy = "application")
  private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<IncomeProof> incomeProofs;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<DebtNotification> debtNotification;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<AssetRepossessionNotice> assetRepossessionNotices;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<LoanRecordRelate> loanRecordRelates;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<LoanPurposeDocument> loanPurposeDocuments;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<LoanCollection> loanCollections;
}
