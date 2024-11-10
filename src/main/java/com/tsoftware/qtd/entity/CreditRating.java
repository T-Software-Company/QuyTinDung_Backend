package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.RatingLevel;
import jakarta.persistence.*;
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

  private Double score;

  @Enumerated(EnumType.STRING)
  private RatingLevel ratingLevel;

  @OneToOne private AppraisalReport appraisalReport;
}
