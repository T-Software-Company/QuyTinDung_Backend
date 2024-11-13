package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.MachineryDto;
import com.tsoftware.qtd.entity.Machinery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MachineryMapper {
  Machinery toEntity(MachineryDto dto);

  MachineryDto toDto(Machinery entity);

  void updateEntity(MachineryDto dto, @MappingTarget Machinery entity);
}
