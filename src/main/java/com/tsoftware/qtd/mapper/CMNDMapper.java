package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.CMNDDto;
import com.tsoftware.qtd.entity.CMND;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CMNDMapper {
  CMND toEntity(CMNDDto dto);

  CMNDDto toDto(CMND entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CMNDDto dto, @MappingTarget CMND entity);
}
