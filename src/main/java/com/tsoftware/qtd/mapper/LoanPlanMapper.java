package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.entity.LoanPlan;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LoanPlanMapper {
  LoanPlan toEntity(LoanPlanDTO dto);

  LoanPlanResponse toDto(LoanPlan entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LoanPlanDTO dto, @MappingTarget LoanPlan entity);
}
