package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
public class RatingFormula extends AbstractAuditEntity {
  private String name;
  private String description;

  @OneToMany(mappedBy = "ratingFormula")
  private List<RatingCriterion> ratingCriteria;
}
