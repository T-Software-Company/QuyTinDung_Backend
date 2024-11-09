package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class AssetType extends AbstractAuditEntity{
    @OneToMany(mappedBy = "assetType")
    private List<Asset> assets;

    @OneToMany(mappedBy = "assetType")
    private List<LandAsset> landAssets;

    @OneToMany(mappedBy = "assetType")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "assetType")
    private List<Apartment> apartments;

    private String name;

}
