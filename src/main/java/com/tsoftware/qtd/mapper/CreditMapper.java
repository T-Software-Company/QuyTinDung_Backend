package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.CreditDto;
import com.tsoftware.qtd.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreditMapper {
  Credit toEntity(CreditDto dto);

  CreditDto toDto(Credit entity);

  void updateEntity(CreditDto dto, @MappingTarget Credit entity);
}
