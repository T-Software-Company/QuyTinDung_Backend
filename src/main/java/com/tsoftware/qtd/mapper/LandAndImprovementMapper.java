package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LandAndImprovementDto;
import com.tsoftware.qtd.entity.LandAndImprovement;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LandAndImprovementMapper {
  LandAndImprovement toEntity(LandAndImprovementDto dto);

  LandAndImprovementDto toDto(LandAndImprovement entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LandAndImprovementDto dto, @MappingTarget LandAndImprovement entity);
}
