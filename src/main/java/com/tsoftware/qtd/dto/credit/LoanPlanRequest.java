package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoanPlanRequest {

  BigDecimal totalCapitalRequirement;
  BigDecimal ownCapital;
  BigDecimal proposedLoanAmount;
  BigDecimal income;
  String repaymentPlan;
  String note;
}
