package com.tsoftware.qtd.dto.loan;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class DisbursementDetail {
  @NotNull(message = "Disbursement amount is required")
  @Positive(message = "Amount must be greater than zero")
  private BigDecimal amount;

  @NotNull(message = "Disbursement date is required")
  @FutureOrPresent(message = "Disbursement date cannot be in the past")
  private ZonedDateTime disbursementDate;

  private String description;
}
