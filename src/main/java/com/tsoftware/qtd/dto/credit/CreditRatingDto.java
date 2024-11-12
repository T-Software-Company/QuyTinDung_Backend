package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
public class CreditRatingDto {

  private BigDecimal score;

  private RatingLevel ratingLevel;
}
