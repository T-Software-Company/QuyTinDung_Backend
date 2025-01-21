package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetResponse {

  private UUID id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal assessedValue;
  private String liquidity;
  private String risk;
  private AssetType assetType;
  private Boolean valuationStatus;

  private ApartmentRequest apartment;
  private LandAndImprovementRequest landAndImprovement;
  private VehicleRequest vehicle;
  private MarketStallsRequest marketStalls;
  private MachineryRequest machinery;
  private OtherAssetRequest otherAsset;
  private LandAssetRequest landAsset;
}
