package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.CCCDDto;
import com.tsoftware.qtd.entity.CCCD;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CCCDMapper {
  CCCD toEntity(CCCDDto dto);

  CCCDDto toDto(CCCD entity);

  void updateEntity(CCCDDto dto, @MappingTarget CCCD entity);
}
