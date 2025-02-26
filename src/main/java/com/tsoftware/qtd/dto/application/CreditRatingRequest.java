package com.tsoftware.qtd.dto.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreditRatingRequest {
  @Valid private RatingByCriteriaRequest ratingByCriteria;
  @Valid private RatingByCICRequest ratingByCIC;

  @NotNull(message = "Application is required")
  @Valid
  private ApplicationRequest application;
}
