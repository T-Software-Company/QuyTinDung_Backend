package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.AssetDto;
import com.tsoftware.qtd.entity.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssetMapper {
  Asset toEntity(AssetDto dto);

  AssetDto toDto(Asset entity);

  void updateEntity(AssetDto dto, @MappingTarget Asset entity);
}
