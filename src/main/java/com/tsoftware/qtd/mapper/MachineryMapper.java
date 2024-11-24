package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.MachineryDto;
import com.tsoftware.qtd.entity.Machinery;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MachineryMapper {
  Machinery toEntity(MachineryDto dto);

  MachineryDto toDto(Machinery entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(MachineryDto dto, @MappingTarget Machinery entity);
}
