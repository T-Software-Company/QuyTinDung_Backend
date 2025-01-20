package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
public class AssetRequest {
  private BigDecimal proposedValue;
  private AssetType assetType;
  private Boolean valuationStatus;
  private List<String> documents;

  private ApartmentRequest apartment;
  private LandAndImprovementRequest landAndImprovement;
  private VehicleRequest vehicle;
  private MarketStallsRequest marketStalls;
  private MachineryRequest machinery;
  private OtherAssetRequest otherAsset;
  private LandAssetRequest landAsset;
  private TypeOfUse typeOfUse;

  @NotNull private ApplicationRequest application;
}
