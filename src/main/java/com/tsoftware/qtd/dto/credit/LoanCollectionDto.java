package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class LoanCollectionDto {

  private BigDecimal amountDue;
  private BigDecimal amountPaid;
  private BigDecimal remainingAmount;
  private ZonedDateTime dueDate;
  private ZonedDateTime paymentDate;
  private String paymentMethod;
  private String status;
  private String note;
}
