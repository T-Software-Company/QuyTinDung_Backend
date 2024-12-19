package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DisbursementDTO {
  private UUID id;
  private BigDecimal amount;
  private ZonedDateTime disbursementDate;
  private String description;
  private DisbursementStatus status;
}
