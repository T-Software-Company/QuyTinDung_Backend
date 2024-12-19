package com.tsoftware.qtd.dto.loan;

import com.tsoftware.qtd.constants.EnumType.RepaymentStatus;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class RepaymentScheduleDTO {
  private UUID loanAccountId;
  private Integer installmentNumber;
  private ZonedDateTime dueDate;
  private BigDecimal principalAmount;
  private BigDecimal interestAmount;
  private BigDecimal totalAmount;
  private RepaymentStatus status;
}
