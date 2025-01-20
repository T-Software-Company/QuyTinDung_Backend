package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.VehicleRequest;
import com.tsoftware.qtd.entity.Vehicle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
  Vehicle toEntity(VehicleRequest dto);

  VehicleRequest toDTO(Vehicle entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(VehicleRequest dto, @MappingTarget Vehicle entity);
}
