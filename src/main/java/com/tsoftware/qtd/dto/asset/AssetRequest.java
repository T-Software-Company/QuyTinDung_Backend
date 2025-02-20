package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.OwnershipType;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
public class AssetRequest {
  private BigDecimal proposedValue;

  @NotNull
  @IsEnum(enumClass = AssetType.class)
  private String assetType;

  @NotNull private List<@NotBlank String> documents;
  @NotNull @NotBlank private String title;

  @NotNull
  @IsEnum(enumClass = OwnershipType.class)
  private String ownershipType;

  @NotNull @Valid private ApplicationRequest application;

  private ApartmentRequest apartment;
  private LandAndImprovementRequest landAndImprovement;
  private VehicleRequest vehicle;
  private MarketStallsRequest marketStalls;
  private MachineryRequest machinery;
  private OtherAssetRequest otherAsset;
  private LandAssetRequest landAsset;
}
