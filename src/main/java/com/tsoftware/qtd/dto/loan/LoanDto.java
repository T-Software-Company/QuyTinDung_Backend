package com.tsoftware.qtd.dto.loan;

import com.tsoftware.qtd.dto.appraisalPlan.AppraisalPlanDto;
import com.tsoftware.qtd.dto.asset.AssetDto;
import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanDto {

  @NotNull(message = "LOAN_ID_REQUIRED")
  Long loanId;

  @NotNull(message = "CUSTOMER_REQUIRED")
  @Valid
  CustomerDto customer;

  @NotNull(message = "LOAN_PLAN_REQUIRED")
  @Valid
  LoanPlanDto loanPlan;

  @NotNull(message = "LOAN_REQUEST_REQUIRED")
  @Valid
  LoanRequestDto loanRequest;

  @NotNull(message = "ASSETS_REQUIRED")
  @Valid
  List<AssetDto> assets;

  @NotNull(message = "APPRAISAL_PLAN_REQUIRED")
  @Valid
  AppraisalPlanDto appraisalPlan;

  @Valid List<DebtNotificationDto> debtNotification;
}
