package com.tsoftware.qtd.dto.application;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingByCICResponse {
  private BigDecimal score;
  private String riskLevel;
  private ZonedDateTime scoringDate;
  private String document;
  private Integer term;
}
