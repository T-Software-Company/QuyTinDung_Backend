package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.LegalStatus;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValuationAssetResponse {
  private BigDecimal value;
  private String liquidity;
  private String risk;
  private BigDecimal depreciationRate;
  private LegalStatus legalStatus;
  private String valuationMethod;
  private String thirdPartyValuationReport;
  private Boolean thirdPartyValuation;

  private Map<String, Object> metadata;
}
