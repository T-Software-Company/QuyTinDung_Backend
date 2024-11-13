package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.Valuation.ValuationReportDto;
import java.util.List;

public interface ValuationReportService {
  ValuationReportDto create(ValuationReportDto valuationreportDto);

  ValuationReportDto update(Long id, ValuationReportDto valuationreportDto);

  void delete(Long id);

  ValuationReportDto getById(Long id);

  List<ValuationReportDto> getAll();
}
