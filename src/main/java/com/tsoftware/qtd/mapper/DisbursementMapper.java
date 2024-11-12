package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.DisbursementDto;
import com.tsoftware.qtd.entity.Disbursement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DisbursementMapper {
  Disbursement toEntity(DisbursementDto dto);

  DisbursementDto toDto(Disbursement entity);

  void updateEntity(DisbursementDto dto, @MappingTarget Disbursement entity);
}
