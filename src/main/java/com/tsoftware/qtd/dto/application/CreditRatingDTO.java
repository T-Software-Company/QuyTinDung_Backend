package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreditRatingDTO {
  private String id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal score;
  private RatingLevel ratingLevel;
}
