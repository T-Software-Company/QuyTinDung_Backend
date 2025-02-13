package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.entity.Approval;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.ValuationReport;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApprovalMapper;
import com.tsoftware.qtd.mapper.ValuationReportMapper;
import com.tsoftware.qtd.repository.ApproveRepository;
import com.tsoftware.qtd.repository.ValuationReportRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValuationReportService {

  private final ValuationReportRepository valuationreportRepository;

  private final ValuationReportMapper valuationreportMapper;

  private final ApproveRepository approveRepository;
  private final ApprovalMapper approvalMapper;

  @Transactional
  public ValuationReportResponse create(ValuationReportRequest valuationreportRequest) {
    ValuationReport valuationreport = valuationreportMapper.toEntity(valuationreportRequest);
    return valuationreportMapper.toResponse(valuationreportRepository.save(valuationreport));
  }

  @Transactional
  public ValuationReportResponse update(UUID id, ValuationReportRequest valuationreportRequest) {
    ValuationReport valuationreport =
        valuationreportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    valuationreportMapper.updateEntity(valuationreportRequest, valuationreport);
    return valuationreportMapper.toResponse(valuationreportRepository.save(valuationreport));
  }

  @Transactional
  public void delete(UUID id) {
    valuationreportRepository.deleteById(id);
  }

  public ValuationReportResponse getById(UUID id) {
    ValuationReport valuationreport =
        valuationreportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    return valuationreportMapper.toResponse(valuationreport);
  }

  public List<ValuationReportResponse> getAll() {
    return valuationreportRepository.findAll().stream()
        .map(valuationreportMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public List<ApprovalResponse> addApprove(UUID id, List<UUID> approverIds) {

    List<ApprovalResponse> approvalResponse = new ArrayList<>();
    approverIds.forEach(
        i ->
            approvalResponse.add(
                approvalMapper.toResponse(
                    approveRepository.save(
                        Approval.builder()
                            .approver(Employee.builder().id(i).build())
                            .status(ActionStatus.WAIT)
                            .build()))));
    return approvalResponse;
  }

  public void removeApprove(UUID id, List<UUID> approverIds) {
    approveRepository.deleteAllByIdInBatch(approverIds);
  }
}
