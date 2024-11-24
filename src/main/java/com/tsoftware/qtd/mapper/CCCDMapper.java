package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.CCCDDto;
import com.tsoftware.qtd.entity.CCCD;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CCCDMapper {
  CCCD toEntity(CCCDDto dto);

  CCCDDto toDto(CCCD entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CCCDDto dto, @MappingTarget CCCD entity);
}
