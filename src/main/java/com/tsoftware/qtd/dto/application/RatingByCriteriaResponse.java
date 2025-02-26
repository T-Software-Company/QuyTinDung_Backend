package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingByCriteriaResponse extends AbstractResponse {
  private BigDecimal score;

  @IsEnum(enumClass = RatingLevel.class)
  private String ratingLevel;

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
