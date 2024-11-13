package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.OtherAssetDto;
import com.tsoftware.qtd.entity.OtherAsset;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OtherAssetMapper {
  OtherAsset toEntity(OtherAssetDto dto);

  OtherAssetDto toDto(OtherAsset entity);

  void updateEntity(OtherAssetDto dto, @MappingTarget OtherAsset entity);
}
