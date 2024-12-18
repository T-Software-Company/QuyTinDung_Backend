package com.tsoftware.qtd.dto.customer;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinancialInfoDTO {
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
