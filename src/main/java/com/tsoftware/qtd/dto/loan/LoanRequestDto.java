package com.tsoftware.qtd.dto.loan;

import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanCollateralType;
import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.entity.LoanPurposeDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class LoanRequestDto {

  @NotNull(message = "LOAN_REQUEST_ID_REQUIRED")
  Long loanRequestId;

  @NotNull(message = "LOAN_REQUIRED")
  @Valid
  LoanDto loan;

  @NotNull(message = "CUSTOMER_REQUIRED")
  @Valid
  CustomerDto customer;

  @NotNull(message = "LOAN_PLAN_REQUIRED")
  @Valid
  LoanPlanDto loanPlan;

  @NotNull(message = "PURPOSE_LOAN_REQUIRED")
  @Valid
  List<LoanPurposeDocument> loanPurposeDocument;

  @NotNull(message = "AMOUNT_REQUIRED")
  @Positive(message = "AMOUNT_MUST_BE_POSITIVE")
  BigDecimal amount;

  @NotNull(message = "PURPOSE_REQUIRED")
  @Size(min = 10, max = 200, message = "PURPOSE_LENGTH_INVALID")
  String purpose;

  @NotNull(message = "BORROWER_TYPE_REQUIRED")
  BorrowerType borrowerType;

  @NotNull(message = "LOAN_TERM_REQUIRED")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date loanTerm;

  @NotNull(message = "LOAN_COLLATERAL_TYPE_REQUIRED")
  LoanCollateralType loanCollateralType;
}
