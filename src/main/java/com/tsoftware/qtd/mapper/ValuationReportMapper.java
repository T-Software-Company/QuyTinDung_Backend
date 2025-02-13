package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.entity.ValuationReport;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ApprovalMapper.class)
public interface ValuationReportMapper {
  ValuationReport toEntity(ValuationReportRequest dto);

  ValuationReportResponse toResponse(ValuationReport entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ValuationReportRequest dto, @MappingTarget ValuationReport entity);
}
