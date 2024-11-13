package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import com.tsoftware.qtd.entity.LoanRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {
  LoanRequest toEntity(LoanRequestRequest dto);

  LoanRequestResponse toResponse(LoanRequest entity);

  void updateEntity(LoanRequestRequest dto, @MappingTarget LoanRequest entity);
}
