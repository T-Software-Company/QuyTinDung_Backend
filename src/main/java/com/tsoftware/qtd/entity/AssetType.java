package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset_type")
public class AssetType extends AbstractAuditEntity {
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "assetId")
  private Asset asset;

  @OneToOne private LandAssetInfo landAssetInfo;

  @OneToOne private VehicleInfo vehicleInfo;

  @OneToOne
  @JoinColumn(name = "apartment_info_id")
  private ApartmentInfo apartmentInfo;

  private String nameAsset;
}
