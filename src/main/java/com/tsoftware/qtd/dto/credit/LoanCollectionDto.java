package com.tsoftware.qtd.dto.credit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class LoanCollectionDto {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal amountDue;
  private BigDecimal amountPaid;
  private BigDecimal remainingAmount;
  private ZonedDateTime dueDate;
  private ZonedDateTime paymentDate;
  private String paymentMethod;
  private String status;
  private String note;
}
