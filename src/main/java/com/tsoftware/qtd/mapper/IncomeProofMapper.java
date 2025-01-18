package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.IncomeProofDTO;
import com.tsoftware.qtd.entity.IncomeProof;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IncomeProofMapper {
  IncomeProof toEntity(IncomeProofDTO dto);

  IncomeProofDTO toDTO(IncomeProof entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(IncomeProofDTO dto, @MappingTarget IncomeProof entity);
}
