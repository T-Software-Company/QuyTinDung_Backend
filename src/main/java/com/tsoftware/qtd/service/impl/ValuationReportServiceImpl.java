package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.Valuation.ValuationReportDto;
import com.tsoftware.qtd.entity.ValuationReport;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ValuationReportMapper;
import com.tsoftware.qtd.repository.ValuationReportRepository;
import com.tsoftware.qtd.service.ValuationReportService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValuationReportServiceImpl implements ValuationReportService {

  @Autowired private ValuationReportRepository valuationreportRepository;

  @Autowired private ValuationReportMapper valuationreportMapper;

  @Override
  public ValuationReportDto create(ValuationReportDto valuationreportDto) {
    ValuationReport valuationreport = valuationreportMapper.toEntity(valuationreportDto);
    return valuationreportMapper.toDto(valuationreportRepository.save(valuationreport));
  }

  @Override
  public ValuationReportDto update(Long id, ValuationReportDto valuationreportDto) {
    ValuationReport valuationreport =
        valuationreportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    valuationreportMapper.updateEntity(valuationreportDto, valuationreport);
    return valuationreportMapper.toDto(valuationreportRepository.save(valuationreport));
  }

  @Override
  public void delete(Long id) {
    valuationreportRepository.deleteById(id);
  }

  @Override
  public ValuationReportDto getById(Long id) {
    ValuationReport valuationreport =
        valuationreportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    return valuationreportMapper.toDto(valuationreport);
  }

  @Override
  public List<ValuationReportDto> getAll() {
    return valuationreportRepository.findAll().stream()
        .map(valuationreportMapper::toDto)
        .collect(Collectors.toList());
  }
}
