package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
public class RatingByCIC extends AbstractAuditEntity {

  private BigDecimal score;
  private String riskLevel;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime scoringDate;

  private String document;
  private Integer term;

  @OneToOne private CreditRating creditRating;
}
