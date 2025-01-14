package com.tsoftware.qtd.dto.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInfoDTO {
  private String jobTitle;
  private String companyName;
  private String companyAddress;
  private Boolean hasMarried;
  private BigDecimal totalIncome;
  private BigDecimal monthlyExpense;
  private BigDecimal monthlySaving;
  private BigDecimal monthlyDebt;
  private BigDecimal monthlyLoanPayment;
  private Set<String> assignees;
  private List<String> files;
  private ApplicationDTO application;
}
