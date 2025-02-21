package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.constants.EnumType.LegalStatus;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class ValuationAssetResponse extends AbstractResponse {
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
