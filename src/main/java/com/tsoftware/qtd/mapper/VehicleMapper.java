package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.VehicleDto;
import com.tsoftware.qtd.entity.Vehicle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
  Vehicle toEntity(VehicleDto dto);

  VehicleDto toDto(Vehicle entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(VehicleDto dto, @MappingTarget Vehicle entity);
}
