package com.tsoftware.qtd.dto.Valuation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class ValuationReportRequest {
  @NotNull(message = "Total valuation amount cannot be null")
  @PositiveOrZero(message = "Total valuation amount must be zero or positive")
  private BigDecimal totalValuationAmount;

  @NonNull
  @NotEmpty(message = "Valuation assets cannot be empty")
  @Valid
  private List<ValuationAssetRequest> valuationAssets;

  private Map<String, Object> metadata;
}
