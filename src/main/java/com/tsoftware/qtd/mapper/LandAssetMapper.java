package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LandAssetDto;
import com.tsoftware.qtd.entity.LandAsset;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LandAssetMapper {
  LandAsset toEntity(LandAssetDto dto);

  LandAssetDto toDto(LandAsset entity);

  void updateEntity(LandAssetDto dto, @MappingTarget LandAsset entity);
}
