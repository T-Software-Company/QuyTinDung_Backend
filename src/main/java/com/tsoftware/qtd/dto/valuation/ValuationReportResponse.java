package com.tsoftware.qtd.dto.valuation;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
  private Application application;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @RequiredArgsConstructor
  public static class Application {
    UUID id;
  }
}
