package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import com.tsoftware.qtd.validation.IsEnum;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingByCriteriaResponse {
  private BigDecimal score;

  @IsEnum(enumClass = RatingLevel.class)
  private String ratingLevel;
}
