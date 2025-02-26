package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingByCICResponse extends AbstractResponse {
  private BigDecimal score;
  private String riskLevel;
  private ZonedDateTime scoringDate;
  private String document;
  private Integer term;
}
