package com.tsoftware.qtd.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoanPlanDTO {

  @NotNull @NotBlank private String loanNeeds;
  @NotNull @NotBlank private BigDecimal totalCapitalRequirement;
  @NotNull @NotBlank private BigDecimal ownCapital;
  @NotNull @NotBlank private BigDecimal proposedLoanAmount;
  @NotNull @NotBlank private BigDecimal monthlyIncome;
  @NotNull @NotBlank private String repaymentPlan;
  private String note;
  @NotNull @NotBlank private String loanTerm;
  private Map<String, Object> metadata;
}
