package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.ApartmentDto;
import com.tsoftware.qtd.entity.Apartment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {
  Apartment toEntity(ApartmentDto dto);

  ApartmentDto toDto(Apartment entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ApartmentDto dto, @MappingTarget Apartment entity);
}
