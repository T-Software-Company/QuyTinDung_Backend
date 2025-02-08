package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.InterestRateType;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "term"})})
public class InterestRateSetting extends AbstractAuditEntity {
  @Enumerated(EnumType.ORDINAL)
  private InterestRateType type;

  private Integer term;
  private BigDecimal rate;
}
