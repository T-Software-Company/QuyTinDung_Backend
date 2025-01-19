package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.entity.Application;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = {
      FinancialInfoMapper.class,
      LoanPlanMapper.class,
      LoanRequestMapper.class,
      EmployeeMapper.class
    })
public interface ApplicationMapper {
  Application toEntity(ApplicationDTO dto);

  ApplicationResponse toResponse(Application entity);

  ApplicationDTO toDTO(Application entity);

  ApplicationDTO toDTO(ApplicationRequest entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ApplicationDTO dto, @MappingTarget Application entity);
}
