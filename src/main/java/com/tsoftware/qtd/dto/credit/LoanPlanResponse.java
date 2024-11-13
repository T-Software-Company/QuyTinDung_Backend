package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoanPlanResponse {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;

  BigDecimal totalCapitalRequirement;
  BigDecimal ownCapital;
  BigDecimal proposedLoanAmount;
  BigDecimal income;
  String repaymentPlan;
  String note;
}
