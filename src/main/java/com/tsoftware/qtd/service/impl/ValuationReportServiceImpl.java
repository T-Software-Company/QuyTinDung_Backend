package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.entity.Approve;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.ValuationReport;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApproveMapper;
import com.tsoftware.qtd.mapper.ValuationReportMapper;
import com.tsoftware.qtd.repository.ApproveRepository;
import com.tsoftware.qtd.repository.ValuationReportRepository;
import com.tsoftware.qtd.service.ValuationReportService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValuationReportServiceImpl implements ValuationReportService {

  @Autowired private ValuationReportRepository valuationreportRepository;

  @Autowired private ValuationReportMapper valuationreportMapper;

  @Autowired private ApproveRepository approveRepository;
  @Autowired private ApproveMapper approveMapper;

  @Override
  @Transactional
  public ValuationReportResponse create(ValuationReportRequest valuationreportRequest) {
    ValuationReport valuationreport = valuationreportMapper.toEntity(valuationreportRequest);
    return valuationreportMapper.toResponse(valuationreportRepository.save(valuationreport));
  }

  @Override
  @Transactional
  public ValuationReportResponse update(UUID id, ValuationReportRequest valuationreportRequest) {
    ValuationReport valuationreport =
        valuationreportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    valuationreportMapper.updateEntity(valuationreportRequest, valuationreport);
    return valuationreportMapper.toResponse(valuationreportRepository.save(valuationreport));
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    valuationreportRepository.deleteById(id);
  }

  @Override
  public ValuationReportResponse getById(UUID id) {
    ValuationReport valuationreport =
        valuationreportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    return valuationreportMapper.toResponse(valuationreport);
  }

  @Override
  public List<ValuationReportResponse> getAll() {
    return valuationreportRepository.findAll().stream()
        .map(valuationreportMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ApproveResponse> addApprove(UUID id, List<UUID> approverIds) {
    var valuationReport =
        valuationreportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    List<ApproveResponse> approveResponses = new ArrayList<>();
    approverIds.forEach(
        i ->
            approveResponses.add(
                approveMapper.toDto(
                    approveRepository.save(
                        Approve.builder()
                            .approver(Employee.builder().id(i).build())
                            .status(ApproveStatus.WAIT)
                            .valuationReport(valuationReport)
                            .build()))));
    return approveResponses;
  }

  @Override
  public void removeApprove(UUID id, List<UUID> approverIds) {
    approveRepository.deleteAllByIdInBatch(approverIds);
  }
}
