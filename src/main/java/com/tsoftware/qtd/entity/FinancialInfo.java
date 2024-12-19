package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table
public class FinancialInfo extends AbstractAuditEntity {
  private String jobTitle;
  private String companyName;
  private String companyAddress;
  private Boolean hasMarried;
  private BigDecimal totalIncome;
  private BigDecimal monthlyExpense;
  private BigDecimal monthlySaving;
  private BigDecimal monthlyDebt;
  private BigDecimal monthlyLoanPayment;

  @OneToOne(fetch = FetchType.LAZY)
  private Application application;
}
