package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.VehicleDTO;
import com.tsoftware.qtd.entity.Vehicle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
  Vehicle toEntity(VehicleDTO dto);

  VehicleDTO toDTO(Vehicle entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(VehicleDTO dto, @MappingTarget Vehicle entity);
}
