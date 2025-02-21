package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.constants.EnumType.LegalStatus;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValuationAssetRequest {
  @NotNull(message = "Value cannot be null")
  @PositiveOrZero(message = "Value must be zero or positive")
  private BigDecimal value;

  private String liquidity;
  private String risk;

  @PositiveOrZero(message = "Depreciation rate must be zero or positive")
  private BigDecimal depreciationRate;

  @IsEnum(enumClass = LegalStatus.class)
  private String legalStatus;

  private String valuationMethod;
  private String thirdPartyValuationReport;
  private Boolean thirdPartyValuation;

  @Valid private Asset asset;

  private Map<String, Object> metadata;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Asset {
    @NotNull(message = "Asset ID cannot be null")
    @IsUUID
    private String id;
  }
}
