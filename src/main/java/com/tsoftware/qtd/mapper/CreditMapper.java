package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import com.tsoftware.qtd.entity.Credit;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = {LoanPlanMapper.class, LoanRequestMapper.class})
public interface CreditMapper {
  Credit toEntity(CreditRequest dto);

  CreditResponse toResponse(Credit entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CreditRequest dto, @MappingTarget Credit entity);
}
