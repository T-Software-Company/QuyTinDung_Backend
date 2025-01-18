package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInfoRequest {
  @IsUUID private String id;
  @NotNull private String jobTitle;
  private String companyName;
  private String companyAddress;
  @NotNull private Boolean hasMarried;
  @NotNull private BigDecimal totalIncome;
  private BigDecimal monthlyExpense;
  private BigDecimal monthlySaving;
  private BigDecimal monthlyDebt;
  private BigDecimal monthlyLoanPayment;
  private List<String> files;
  @NotNull @Valid private ApplicationRequest application;
}
