package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreditResponse extends AbstractResponse {

  private BigDecimal amount;
  private ZonedDateTime startDate;
  private ZonedDateTime dueDate;
  private BigDecimal interestRate;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;
  private LoanStatus status;
  private LoanSecurityType loanSecurityType;
}
