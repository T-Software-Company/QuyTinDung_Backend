package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.ValuationAssetDTO;
import com.tsoftware.qtd.entity.ValuationAsset;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ValuationAssetMapper {
  ValuationAsset toEntity(ValuationAssetDTO DTO);

  ValuationAssetDTO toDTO(ValuationAsset entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ValuationAssetDTO DTO, @MappingTarget ValuationAsset entity);
}
