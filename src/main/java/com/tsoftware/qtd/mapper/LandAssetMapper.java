package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LandAssetDto;
import com.tsoftware.qtd.entity.LandAsset;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LandAssetMapper {
  LandAsset toEntity(LandAssetDto dto);

  LandAssetDto toDto(LandAsset entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LandAssetDto dto, @MappingTarget LandAsset entity);
}
