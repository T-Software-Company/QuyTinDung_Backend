package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class DisbursementDTO {
  private UUID id;
  private BigDecimal amount;
  private ZonedDateTime disbursementDate;
  private String description;
  private DisbursementStatus status;
}
