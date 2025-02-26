package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreditRatingRequest {
  @NotNull(message = "Score is required")
  @DecimalMin(value = "0.0", message = "Score must be positive")
  private BigDecimal score;

  @NotNull(message = "Rating level is required")
  private RatingLevel ratingLevel;

  @NotNull(message = "Application is required")
  @Valid
  private ApplicationRequest application;
}
