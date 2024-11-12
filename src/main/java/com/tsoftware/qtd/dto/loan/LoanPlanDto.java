package com.tsoftware.qtd.dto.loan;

import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.entity.LoanPurposeDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

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
