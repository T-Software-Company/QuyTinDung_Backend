package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class Disbursement {

  private BigDecimal loanLimit;
  private BigDecimal amountReceived;
  private BigDecimal currentOutstandingDebt;
  private ZonedDateTime dateOfLoanReceipt;
  private ZonedDateTime loanTerm;
  private BigDecimal interestRate;
  private String repaymentSchedule;
}
