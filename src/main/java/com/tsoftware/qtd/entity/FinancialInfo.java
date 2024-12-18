package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "financial_info")
public class FinancialInfo extends AbstractAuditEntity {
  @NotNull private String jobTitle;
  private String companyName;
  private String companyAddress;
  @NotNull private Boolean hasMarried;
  @NotNull private BigDecimal totalIncome;
  private BigDecimal monthlyExpense;
  private BigDecimal monthlySaving;
  private BigDecimal monthlyDebt;
  private BigDecimal monthlyLoanPayment;
}
