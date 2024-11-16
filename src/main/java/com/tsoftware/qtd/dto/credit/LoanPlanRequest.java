package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoanPlanRequest {

  private BigDecimal totalCapitalRequirement;
  private BigDecimal ownCapital;
  private BigDecimal proposedLoanAmount;
  private BigDecimal income;
  private String repaymentPlan;
  private String note;
  private String documentUrl;
  private Map<String, Object> metadata;
}
