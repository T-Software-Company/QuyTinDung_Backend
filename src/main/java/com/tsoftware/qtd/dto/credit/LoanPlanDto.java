package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoanPlanDto {

  Long id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  BigDecimal totalCapitalRequirement;
  BigDecimal ownCapital;
  BigDecimal proposedLoanAmount;
  BigDecimal income;
  String repaymentPlan;
  String note;
}
