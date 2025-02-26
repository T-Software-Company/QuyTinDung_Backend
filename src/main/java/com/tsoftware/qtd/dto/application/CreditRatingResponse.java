package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.dto.AbstractResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreditRatingResponse extends AbstractResponse {
  private RatingByCriteriaResponse ratingByCriteria;
  private RatingByCICResponse ratingByCIC;

  @Valid private Application application;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @RequiredArgsConstructor
  public static class Application {
    UUID id;
  }
}
