package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.entity.Disbursement;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DisbursementMapper {
  Disbursement toEntity(DisbursementDTO dto);

  DisbursementDTO toDTO(Disbursement entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(DisbursementDTO dto, @MappingTarget Disbursement entity);
}
