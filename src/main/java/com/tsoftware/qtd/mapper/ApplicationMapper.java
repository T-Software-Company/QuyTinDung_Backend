package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.ApplicationRequest;
import com.tsoftware.qtd.dto.credit.ApplicationResponse;
import com.tsoftware.qtd.entity.Application;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = {LoanPlanMapper.class, LoanRequestMapper.class})
public interface ApplicationMapper {
  Application toEntity(ApplicationRequest dto);

  ApplicationResponse toResponse(Application entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ApplicationRequest dto, @MappingTarget Application entity);
}
