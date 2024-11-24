package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.PassPortDto;
import com.tsoftware.qtd.entity.PassPort;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PassPortMapper {
  PassPort toEntity(PassPortDto dto);

  PassPortDto toDto(PassPort entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(PassPortDto dto, @MappingTarget PassPort entity);
}
