package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
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
  private Map<String, Object> metadata;
  @NotNull @Valid private ApplicationRequest application;
}
