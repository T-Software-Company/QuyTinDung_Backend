package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.OtherAssetDto;
import com.tsoftware.qtd.entity.OtherAsset;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OtherAssetMapper {
  OtherAsset toEntity(OtherAssetDto dto);

  OtherAssetDto toDTO(OtherAsset entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(OtherAssetDto dto, @MappingTarget OtherAsset entity);
}
