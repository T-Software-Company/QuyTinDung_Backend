package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.OwnershipType;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetResponse extends AbstractResponse {
  private BigDecimal proposedValue;
  private AssetType assetType;
  private Boolean valuationStatus;
  private OwnershipType ownershipType;
  private String title;
  private List<String> documents;
  private ValuationAssetResponse valuationAsset;

  private ApartmentRequest apartment;
  private LandAndImprovementRequest landAndImprovement;
  private VehicleRequest vehicle;
  private MarketStallsRequest marketStalls;
  private MachineryRequest machinery;
  private OtherAssetRequest otherAsset;
  private LandAssetRequest landAsset;
}
