package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.RatingCriterionType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingCriterionDTO {
  private UUID id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal weigh;
  private Integer coefficient;
  private Integer score;
  private String title;
  private RatingCriterionType ratingCriterionType;
}
