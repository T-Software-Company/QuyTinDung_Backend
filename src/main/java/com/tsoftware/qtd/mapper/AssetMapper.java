package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
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
      VehicleMapper.class,
      ApartmentMapper.class
    })
public interface AssetMapper {
  Asset toEntity(AssetRequest dto);

  AssetResponse toResponse(Asset entity);

  void updateEntity(AssetRequest dto, @MappingTarget Asset entity);
}
