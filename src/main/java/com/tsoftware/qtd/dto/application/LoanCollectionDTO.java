package com.tsoftware.qtd.dto.application;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoanCollectionDTO {
  private UUID id;
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
