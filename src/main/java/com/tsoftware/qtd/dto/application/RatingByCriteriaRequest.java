package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingByCriteriaRequest {
  @NotNull(message = "Score is required")
  @DecimalMin(value = "0.0", message = "Score must be positive")
  private BigDecimal score;

  @NotNull(message = "Rating level is required")
  @IsEnum(enumClass = RatingLevel.class)
  private String ratingLevel;
}
