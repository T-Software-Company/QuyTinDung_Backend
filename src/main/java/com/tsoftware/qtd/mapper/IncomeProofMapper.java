package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.IncomeProofDto;
import com.tsoftware.qtd.entity.IncomeProof;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IncomeProofMapper {
  IncomeProof toEntity(IncomeProofDto dto);

  IncomeProofDto toDto(IncomeProof entity);

  void updateEntity(IncomeProofDto dto, @MappingTarget IncomeProof entity);
}
