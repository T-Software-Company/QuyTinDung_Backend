package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
public class DisbursementDto {
  private UUID id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;

  private BigDecimal loanLimit;
  private BigDecimal amountReceived;
  private BigDecimal currentOutstandingDebt;
  private ZonedDateTime dateOfLoanReceipt;
  private ZonedDateTime loanTerm;
  private BigDecimal interestRate;
  private String repaymentSchedule;
}
