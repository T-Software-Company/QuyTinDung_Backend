package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.entity.Approval;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.ValuationReport;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApprovalMapper;
import com.tsoftware.qtd.mapper.ValuationReportMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.ApproveRepository;
import com.tsoftware.qtd.repository.AssetRepository;
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

  private final ValuationReportRepository valuationReportRepository;

  private final ValuationReportMapper valuationReportMapper;

  private final ApproveRepository approveRepository;
  private final ApprovalMapper approvalMapper;
  private final ApprovalProcessService approvalProcessService;
  private final AssetRepository assetRepository;
  private final ApplicationRepository applicationRepository;

  public ApprovalProcessResponse request(ValuationReportRequest valuationReportRequest) {

    return approvalProcessService.create(
        valuationReportRequest,
        valuationReportRequest.getApplication(),
        ProcessType.CREATE_VALUATION_REPORT);
  }

  public ValuationReportResponse create(ValuationReportRequest valuationReportRequest) {
    ValuationReport valuationReport = valuationReportMapper.toEntity(valuationReportRequest);
    var application =
        applicationRepository
            .findById(UUID.fromString(valuationReportRequest.getApplication().getId()))
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Application not found: "
                            + valuationReportRequest.getApplication().getId()));
    valuationReport.setApplication(application);
    var valuationAssets = valuationReport.getValuationAssets();
    valuationAssets.forEach(
        v -> {
          v.setValuationReport(valuationReport);
          var assetSaved =
              assetRepository
                  .findById(v.getAsset().getId())
                  .orElseThrow(
                      () ->
                          new CommonException(
                              ErrorType.ENTITY_NOT_FOUND,
                              "Asset not found: " + v.getAsset().getId()));
          v.setAsset(assetSaved);
        });
    return valuationReportMapper.toResponse(valuationReportRepository.save(valuationReport));
  }

  public ValuationReportResponse update(UUID id, ValuationReportRequest valuationReportRequest) {
    ValuationReport valuationReport =
        valuationReportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    valuationReportMapper.updateEntity(valuationReportRequest, valuationReport);
    return valuationReportMapper.toResponse(valuationReportRepository.save(valuationReport));
  }

  public void delete(UUID id) {
    valuationReportRepository.deleteById(id);
  }

  public ValuationReportResponse getById(UUID id) {
    ValuationReport valuationReport =
        valuationReportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationReport not found"));
    return valuationReportMapper.toResponse(valuationReport);
  }

  public List<ValuationReportResponse> getAll() {
    return valuationReportRepository.findAll().stream()
        .map(valuationReportMapper::toResponse)
        .collect(Collectors.toList());
  }

  public List<ApprovalResponse> addApprove(UUID id, List<UUID> approverIds) {

    List<ApprovalResponse> approvalResponse = new ArrayList<>();
    approverIds.forEach(
        i ->
            approvalResponse.add(
                approvalMapper.toResponse(
                    approveRepository.save(
                        Approval.builder()
                            .approver(Employee.builder().id(i).build())
                            .status(ApprovalStatus.WAIT)
                            .build()))));
    return approvalResponse;
  }

  public void removeApprove(UUID id, List<UUID> approverIds) {
    approveRepository.deleteAllByIdInBatch(approverIds);
  }
}
