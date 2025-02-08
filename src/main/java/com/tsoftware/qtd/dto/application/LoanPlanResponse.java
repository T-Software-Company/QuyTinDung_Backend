package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanPlanResponse extends AbstractResponse {
  private BigDecimal totalCapitalRequirement;
  private BigDecimal ownCapital;
  private BigDecimal proposedLoanAmount;
  private BigDecimal monthlyIncome;
  private String repaymentPlan;
  private String note;
  private Integer loanTerm;
  private BigDecimal interestRate;
  private Map<String, Object> metadata;
}
