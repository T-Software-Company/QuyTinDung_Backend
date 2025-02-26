package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.RatingCriterionType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class RatingCriterionSetting extends AbstractAuditEntity {
  private Integer weigh;
  private Integer coefficient;
  private String title;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, BigDecimal> scoreMapping;

  @Enumerated(EnumType.ORDINAL)
  private RatingCriterionType ratingCriterionType;
}
