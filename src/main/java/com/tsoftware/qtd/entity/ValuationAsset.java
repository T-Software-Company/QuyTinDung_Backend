package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.LegalStatus;
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

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationAsset extends AbstractAuditEntity {
  private BigDecimal value;
  private String liquidity;
  private String risk;
  private BigDecimal depreciationRate;
  private LegalStatus legalStatus;
  private String valuationMethod;
  private String thirdPartyValuationReport;
  private Boolean thirdPartyValuation;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;
}
