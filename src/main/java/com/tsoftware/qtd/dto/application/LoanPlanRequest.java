package com.tsoftware.qtd.dto.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanPlanRequest {
  @NotNull private BigDecimal totalCapitalRequirement;
  @NotNull private BigDecimal ownCapital;
  @NotNull private BigDecimal proposedLoanAmount;
  @NotNull private BigDecimal monthlyIncome;
  @NotNull @NotNull private String repaymentPlan;
  private String note;
  @NotNull private Integer loanTerm;
  @NotNull private BigDecimal interestRate;
  private Map<String, Object> metadata;
  private Set<String> assignees;
  @NotNull @Valid private ApplicationRequest application;
}
