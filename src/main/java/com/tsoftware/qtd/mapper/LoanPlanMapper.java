package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.LoanPlanRequest;
import com.tsoftware.qtd.dto.credit.LoanPlanResponse;
import com.tsoftware.qtd.entity.LoanPlan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoanPlanMapper {
  LoanPlan toEntity(LoanPlanRequest dto);

  LoanPlanResponse toDto(LoanPlan entity);

  void updateEntity(LoanPlanRequest dto, @MappingTarget LoanPlan entity);
}
