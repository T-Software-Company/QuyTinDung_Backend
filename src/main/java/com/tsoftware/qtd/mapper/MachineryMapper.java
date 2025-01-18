package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.MachineryDTO;
import com.tsoftware.qtd.entity.Machinery;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MachineryMapper {
  Machinery toEntity(MachineryDTO dto);

  MachineryDTO toDTO(Machinery entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(MachineryDTO dto, @MappingTarget Machinery entity);
}
