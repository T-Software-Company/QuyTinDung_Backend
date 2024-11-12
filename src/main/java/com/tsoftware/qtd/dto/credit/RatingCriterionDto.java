package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.RatingCriterionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
public class RatingCriterionDto {
  private BigDecimal weigh;
  private Integer coefficient;
  private Integer score;
  private String title;
  private RatingCriterionType ratingCriterionType;
}
