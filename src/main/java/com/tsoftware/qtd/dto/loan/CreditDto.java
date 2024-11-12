package com.tsoftware.qtd.dto.loan;

import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditDto {


    Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String lastModifiedBy;
    private String createdBy;
    private BigDecimal amount;
    private ZonedDateTime startDate;
    private ZonedDateTime dueDate;
    private BigDecimal interestRate;
    private BigDecimal amountPaid;
    private BigDecimal currentOutstandingDebt;
    private LoanStatus status;
    private LoanSecurityType loanSecurityType;
}
