package com.tsoftware.qtd.dto.loan;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class LoanAccountDTO {
  private UUID id;
  private UUID applicationId;
  private String accountNumber;
  private BigDecimal approvedAmount;
  private BigDecimal disbursedAmount;
  private BigDecimal interestRate;
  private Integer termInMonths;
  private ZonedDateTime approvalDate;
}
