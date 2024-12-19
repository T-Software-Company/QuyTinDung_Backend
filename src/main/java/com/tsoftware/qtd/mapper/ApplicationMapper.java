package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.entity.Application;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = {LoanPlanMapper.class, LoanRequestMapper.class})
public interface ApplicationMapper {
  Application toEntity(ApplicationDTO dto);

  ApplicationResponse toResponse(Application entity);

  ApplicationDTO toDTO(Application entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ApplicationDTO dto, @MappingTarget Application entity);
}
