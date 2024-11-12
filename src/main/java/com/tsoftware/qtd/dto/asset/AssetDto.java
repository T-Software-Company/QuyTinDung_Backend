package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.entity.AppraisalReport;
import com.tsoftware.qtd.entity.Credit;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetDto {


  Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal assessedValue;
  private String liquidity;
  private String risk;
  private AssetType assetType;
}
