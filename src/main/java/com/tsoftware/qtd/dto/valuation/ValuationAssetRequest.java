package com.tsoftware.qtd.dto.valuation;

import com.tsoftware.qtd.constants.EnumType.LegalStatus;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
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

  @NotBlank(message = "Liquidity cannot be blank")
  private String liquidity;

  @NotBlank(message = "Risk cannot be blank")
  private String risk;

  @NotNull(message = "Depreciation rate cannot be null")
  @PositiveOrZero(message = "Depreciation rate must be zero or positive")
  @Max(value = 100, message = "Depreciation rate must not exceed 100")
  private BigDecimal depreciationRate;

  @NotBlank(message = "Legal status cannot be blank")
  @IsEnum(enumClass = LegalStatus.class, message = "Invalid legal status")
  private String legalStatus;

  @NotBlank(message = "Valuation method cannot be blank")
  private String valuationMethod;

  @Pattern(
      regexp = "^(https?://.*|)$",
      message = "Third party valuation report must be a valid URL")
  private String thirdPartyValuationReport;

  @NotNull(message = "Third party valuation flag cannot be null")
  private Boolean thirdPartyValuation;

  @Size(min = 1, message = "At least one document is required")
  private List<@NotBlank(message = "Document path cannot be blank") String> documents;

  @Valid
  @NotNull(message = "Asset cannot be null")
  private Asset asset;

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
