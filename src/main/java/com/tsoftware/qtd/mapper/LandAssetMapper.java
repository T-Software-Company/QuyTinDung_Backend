package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LandAssetDTO;
import com.tsoftware.qtd.entity.LandAsset;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LandAssetMapper {
  LandAsset toEntity(LandAssetDTO dto);

  LandAssetDTO toDTO(LandAsset entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LandAssetDTO dto, @MappingTarget LandAsset entity);
}
