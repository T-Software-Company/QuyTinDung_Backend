package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class LoanAccount extends AbstractAuditEntity {

  @Column(unique = true, nullable = false)
  private String accountNumber;

  private BigDecimal approvedAmount;
  private BigDecimal disbursedAmount;
  private BigDecimal interestRate;
  private Integer termInMonths;
  private ZonedDateTime approvalDate;
  private LoanStatus status;

  @OneToMany(mappedBy = "loanAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Disbursement> disbursements = new ArrayList<>();

  @OneToMany(mappedBy = "loanAccount", cascade = CascadeType.ALL)
  private List<RepaymentSchedule> repaymentSchedule = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Application application;
}
