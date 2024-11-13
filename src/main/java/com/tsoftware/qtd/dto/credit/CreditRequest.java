package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditRequest {

  private BigDecimal amount;
  private ZonedDateTime startDate;
  private ZonedDateTime dueDate;
  private BigDecimal interestRate;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;
  private LoanStatus status;
  private LoanSecurityType loanSecurityType;
  private LoanPlanRequest loanPlan;
  private LoanRequestRequest loanRequest;
}
