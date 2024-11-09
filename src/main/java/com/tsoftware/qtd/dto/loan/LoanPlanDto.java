package com.tsoftware.qtd.dto.loan;

import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.entity.PurposeLoanRelated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class LoanPlanDto {

    @NotNull(message = "LOAN_PLAN_ID_REQUIRED")
    Long loanPlanId;

    @NotNull(message = "CUSTOMER_REQUIRED")
    @Valid
    CustomerDto customer;

    @NotNull(message = "LOAN_REQUIRED")
    @Valid
    LoanDto loan;

    @NotNull(message = "LOAN_REQUEST_REQUIRED")
    @Valid
    LoanRequestDto loanRequest;

    @NotNull(message = "PURPOSE_LOAN_REQUIRED")
    @Valid
    PurposeLoanRelated purposeLoanRelated;
}
