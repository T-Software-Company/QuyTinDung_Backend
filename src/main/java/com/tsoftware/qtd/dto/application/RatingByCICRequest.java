package com.tsoftware.qtd.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
public class RatingByCICRequest {
  @NotNull(message = "Score must not be null")
  @Positive(message = "Score must be positive")
  private BigDecimal score;

  @NotBlank(message = "Risk level must not be blank")
  private String riskLevel;

  @NotNull(message = "Scoring date must not be null")
  private ZonedDateTime scoringDate;

  @NotBlank(message = "Document must not be blank")
  private String document;

  @NotNull(message = "Term must not be null")
  @Positive(message = "Term must be positive")
  private Integer term;
}
