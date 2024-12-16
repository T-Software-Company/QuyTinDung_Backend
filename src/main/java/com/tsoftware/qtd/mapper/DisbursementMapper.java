package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.DisbursementDto;
import com.tsoftware.qtd.entity.Disbursement;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DisbursementMapper {
  Disbursement toEntity(DisbursementDto dto);

  DisbursementDto toDto(Disbursement entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(DisbursementDto dto, @MappingTarget Disbursement entity);
}
