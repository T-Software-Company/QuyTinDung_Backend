package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.ApartmentDTO;
import com.tsoftware.qtd.entity.Apartment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {
  Apartment toEntity(ApartmentDTO dto);

  ApartmentDTO toDTO(Apartment entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ApartmentDTO dto, @MappingTarget Apartment entity);
}
