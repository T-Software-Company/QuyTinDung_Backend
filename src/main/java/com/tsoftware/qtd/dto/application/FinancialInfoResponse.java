package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInfoResponse extends AbstractResponse {
  private String jobTitle;
  private String companyName;
  private String companyAddress;
  private Boolean hasMarried;
  private BigDecimal totalIncome;
  private BigDecimal monthlyExpense;
  private BigDecimal monthlySaving;
  private BigDecimal monthlyDebt;
  private BigDecimal monthlyLoanPayment;
  private List<String> files;
}
