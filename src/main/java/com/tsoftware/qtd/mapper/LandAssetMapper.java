package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LandAssetRequest;
import com.tsoftware.qtd.entity.LandAsset;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LandAssetMapper {
  LandAsset toEntity(LandAssetRequest dto);

  LandAssetRequest toDTO(LandAsset entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LandAssetRequest dto, @MappingTarget LandAsset entity);
}
