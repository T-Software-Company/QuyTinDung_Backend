package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.AssetDto;
import com.tsoftware.qtd.entity.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {
      LandAssetMapper.class,
      LandAndImprovementMapper.class,
      MachineryMapper.class,
      MarketStallsMapper.class,
      OtherAssetMapper.class,
      VehicleMapper.class
    })
public interface AssetMapper {
  Asset toEntity(AssetDto dto);

  AssetDto toDto(Asset entity);

  void updateEntity(AssetDto dto, @MappingTarget Asset entity);
}
