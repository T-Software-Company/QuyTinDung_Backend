package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.PassPortDto;
import com.tsoftware.qtd.entity.PassPort;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassPortMapper {
  PassPort toEntity(PassPortDto dto);

  PassPortDto toDto(PassPort entity);

  void updateEntity(PassPortDto dto, @MappingTarget PassPort entity);
}
