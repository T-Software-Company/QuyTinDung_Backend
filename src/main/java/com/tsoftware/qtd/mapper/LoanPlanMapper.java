package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.entity.LoanPlan;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LoanPlanMapper {
  LoanPlan toEntity(LoanPlanRequest dto);

  LoanPlanResponse toDTO(LoanPlan entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LoanPlanRequest dto, @MappingTarget LoanPlan entity);
}
