package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import com.tsoftware.qtd.entity.LoanRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {
  LoanRequest toEntity(LoanRequestRequest request);

  LoanRequestResponse toResponse(LoanRequest entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LoanRequestRequest request, @MappingTarget LoanRequest entity);
}
