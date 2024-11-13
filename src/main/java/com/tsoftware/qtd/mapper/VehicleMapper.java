package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.VehicleDto;
import com.tsoftware.qtd.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
  Vehicle toEntity(VehicleDto dto);

  VehicleDto toDto(Vehicle entity);

  void updateEntity(VehicleDto dto, @MappingTarget Vehicle entity);
}
