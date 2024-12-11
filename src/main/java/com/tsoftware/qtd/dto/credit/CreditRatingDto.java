package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditRatingDto {
  private UUID id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal score;

  private RatingLevel ratingLevel;
}
