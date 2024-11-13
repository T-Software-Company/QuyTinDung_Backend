package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.Valuation.ValuationReportDto;
import com.tsoftware.qtd.entity.ValuationReport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ValuationReportMapper {
  ValuationReport toEntity(ValuationReportDto dto);

  ValuationReportDto toDto(ValuationReport entity);

  void updateEntity(ValuationReportDto dto, @MappingTarget ValuationReport entity);
}
