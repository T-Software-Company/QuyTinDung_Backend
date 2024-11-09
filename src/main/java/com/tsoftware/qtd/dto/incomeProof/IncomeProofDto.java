package com.tsoftware.qtd.dto.incomeProof;

import com.tsoftware.qtd.constants.EnumType.IncomeProofType;
import com.tsoftware.qtd.dto.appraisalPlan.AppraisalPlanDto;
import com.tsoftware.qtd.dto.customer.CustomerDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class IncomeProofDto {

  @NotNull(message = "ID_REQUIRED")
  Long id;

  @NotNull(message = "CREATED_AT_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime createdAt;

  @NotNull(message = "UPDATED_AT_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime updatedAt;

  String lastModifiedBy;
  String createdBy;

  @NotNull(message = "CUSTOMER_REQUIRED")
  CustomerDto customer;

  @NotNull(message = "APPRAISAL_PLAN_REQUIRED")
  AppraisalPlanDto appraisalPlan;

  @NotBlank(message = "LINK_FILE_REQUIRED")
  @Size(max = 255, message = "LINK_FILE_TOO_LONG")
  String linkFile;

  @NotNull(message = "INCOME_PROOF_TYPE_REQUIRED")
  IncomeProofType incomeProofType;
}
