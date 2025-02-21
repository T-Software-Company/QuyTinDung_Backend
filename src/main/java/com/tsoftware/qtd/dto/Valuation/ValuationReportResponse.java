package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class ValuationReportResponse extends AbstractResponse {

  private BigDecimal totalValuationAmount;
  private List<ValuationAssetResponse> valuationAssets;
  private Map<String, Object> metadata;
}
