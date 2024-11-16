package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
public class AssetRequest {

  private BigDecimal assessedValue;
  private String liquidity;
  private String risk;
  private AssetType assetType;
  private Boolean valuationStatus;
  private List<LegalDocumentRequest> legalDocuments;

  private ApartmentDto apartment;
  private LandAndImprovementDto landAndImprovement;
  private VehicleDto vehicle;
  private MarketStallsDto marketStalls;
  private MachineryDto machinery;
  private OtherAssetDto otherAsset;
  private LandAssetDto landAsset;
}
