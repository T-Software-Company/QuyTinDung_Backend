package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class CreditRating extends AbstractAuditEntity {

  private BigDecimal score;

  @Enumerated(EnumType.ORDINAL)
  private RatingLevel ratingLevel;

  @OneToOne(fetch = FetchType.LAZY)
  private AppraisalReport appraisalReport;
}
