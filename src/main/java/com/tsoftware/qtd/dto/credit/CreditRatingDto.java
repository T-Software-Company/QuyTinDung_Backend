package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class CreditRatingDto {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal score;

  private RatingLevel ratingLevel;
}
