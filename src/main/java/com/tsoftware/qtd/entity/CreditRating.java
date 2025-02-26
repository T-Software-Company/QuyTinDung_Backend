package com.tsoftware.qtd.entity;

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

  @OneToOne(mappedBy = "creditRating", cascade = CascadeType.ALL)
  private RatingByCriteria ratingByCriteria;

  @OneToOne(mappedBy = "creditRating", cascade = CascadeType.ALL)
  private RatingByCIC ratingByCIC;

  @OneToOne(fetch = FetchType.LAZY)
  private Application application;
}
