package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.ApartmentDto;
import com.tsoftware.qtd.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {
  Apartment toEntity(ApartmentDto dto);

  ApartmentDto toDto(Apartment entity);

  void updateEntity(ApartmentDto dto, @MappingTarget Apartment entity);
}
