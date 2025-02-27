package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.OwnershipType;
import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.valuation.ValuationAssetResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class AssetResponse extends AbstractResponse {
  private BigDecimal proposedValue;
  private AssetType assetType;
  private Boolean valuationStatus;
  private OwnershipType ownershipType;
  private String title;
  private List<String> documents;
  private ValuationAssetResponse valuationAsset;

  private ApartmentDTO apartment;
  private LandAndImprovementDTO landAndImprovement;
  private VehicleDTO vehicle;
  private MarketStallsDTO marketStalls;
  private MachineryDTO machinery;
  private OtherAssetRequest otherAsset;
  private LandAssetDTO landAsset;
}
