package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.FinancialInfoDTO;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;
import com.tsoftware.qtd.entity.FinancialInfo;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FinancialInfoMapper {
  FinancialInfo toEntity(FinancialInfoRequest request);

  FinancialInfoDTO toDTO(FinancialInfo entity);

  FinancialInfoRequest toRequest(FinancialInfo dto);

  FinancialInfoResponse toResponse(FinancialInfo entity);

  void updateEntity(FinancialInfoRequest request, @MappingTarget FinancialInfo entity);
}
