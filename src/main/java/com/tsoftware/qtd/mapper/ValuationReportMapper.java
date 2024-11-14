package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.entity.ValuationReport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ApproveMapper.class)
public interface ValuationReportMapper {
  ValuationReport toEntity(ValuationReportRequest dto);

  ValuationReportResponse toResponse(ValuationReport entity);

  void updateEntity(ValuationReportRequest dto, @MappingTarget ValuationReport entity);
}
