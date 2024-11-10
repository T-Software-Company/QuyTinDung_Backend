package com.tsoftware.qtd.dto.appraisalPlan;

import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.entity.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppraisalPlanDto {

  @NotNull(message = "APPRAISAL_PLAN_ID_REQUIRED")
  Long appraisalPlanId;

  @NotNull(message = "CUSTOMER_REQUIRED")
  @Valid
  CustomerDto customer;

  @NotNull(message = "PURPOSE_LOAN_RELATED_REQUIRED")
  LoanPurposeDocument loanPurposeDocument;

  @NotNull(message = "LOAN_REQUIRED")
  Credit credit;

  @NotNull(message = "EMPLOYEE_REQUIRED")
  Employee employee;

  @NotNull(message = "INCOME_PROOF_REQUIRED")
  IncomeProof incomeProof;

  @NotNull(message = "APPRAISAL_REPORT_REQUIRED")
  AppraisalReport appraisalReport;

  @NotBlank(message = "ADDRESS_APPRAISAL_REQUIRED")
  @Size(min = 5, max = 255, message = "INVALID_ADDRESS_APPRAISAL_LENGTH")
  String addressAppraisal;

  @NotBlank(message = "PARTICIPANTS_REQUIRED")
  String participants;

  @NotNull(message = "START_DATE_APPRAISAL_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  Date startDateAppraisal;

  @NotNull(message = "END_DATE_APPRAISAL_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  Date endDateAppraisal;
}
