package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationReport extends AbstractAuditEntity {
  private BigDecimal totalValuationAmount;

  @OneToMany(
      mappedBy = "valuationReport",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private List<ValuationAsset> valuationAssets;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToOne(fetch = FetchType.LAZY)
  private Application application;
}
