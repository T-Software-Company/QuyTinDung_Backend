package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import com.tsoftware.qtd.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {LoanPlanMapper.class, LoanRequestMapper.class})
public interface CreditMapper {
  Credit toEntity(CreditRequest dto);

  CreditResponse toResponse(Credit entity);

  void updateEntity(CreditRequest dto, @MappingTarget Credit entity);
}
