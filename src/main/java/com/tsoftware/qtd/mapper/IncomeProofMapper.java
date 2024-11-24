package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.IncomeProofDto;
import com.tsoftware.qtd.entity.IncomeProof;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IncomeProofMapper {
  IncomeProof toEntity(IncomeProofDto dto);

  IncomeProofDto toDto(IncomeProof entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(IncomeProofDto dto, @MappingTarget IncomeProof entity);
}
