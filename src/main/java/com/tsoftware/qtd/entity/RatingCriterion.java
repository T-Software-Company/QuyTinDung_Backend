package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.RatingCriterionType;
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
public class RatingCriterion extends AbstractAuditEntity {
  private BigDecimal weigh;
  private Integer coefficient;
  private Integer score;
  private String title;

  @Enumerated(EnumType.STRING)
  private RatingCriterionType ratingCriterionType;

  @ManyToOne private RatingFormula ratingFormula;
}
