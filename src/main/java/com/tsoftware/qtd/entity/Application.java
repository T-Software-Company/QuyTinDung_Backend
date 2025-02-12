package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
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

@Getter
@Setter
@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Application extends AbstractAuditEntity {

  private BigDecimal amount;
  private BigDecimal interestRate;
  private Integer loanTerm;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;
  private String purpose;

  @Enumerated(EnumType.ORDINAL)
  private BorrowerType borrowerType;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime startDate;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime dueDate;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.ORDINAL)
  private LoanStatus status;

  @Enumerated(EnumType.ORDINAL)
  private LoanSecurityType loanSecurityType;

  @ManyToMany(mappedBy = "applicationsAssigned", fetch = FetchType.LAZY)
  private List<Employee> loanProcessors;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<ApprovalProcess> approvalProcess;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @OneToOne(mappedBy = "application")
  private LoanRequest loanRequest;

  @OneToOne(mappedBy = "application")
  private LoanPlan loanPlan;

  @OneToOne(mappedBy = "application")
  private FinancialInfo financialInfo;

  @OneToOne(mappedBy = "application")
  private ValuationMeeting valuationMeeting;

  @OneToMany(mappedBy = "application")
  private List<Asset> assets;

  @OneToOne(mappedBy = "application")
  private AppraisalPlan appraisalPlan;

  @OneToOne(mappedBy = "application")
  private LoanAccount loanAccount;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
  private List<LoanCollection> loanCollections;
}
