package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.appraisal.AppraisalReportRequest;
import com.tsoftware.qtd.dto.appraisal.AppraisalReportResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.entity.AppraisalReport;
import com.tsoftware.qtd.entity.Approval;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AppraisalReportMapper;
import com.tsoftware.qtd.mapper.ApprovalMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.AppraisalReportRepository;
import com.tsoftware.qtd.repository.ApproveRepository;
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
public class AppraisalReportService {

  private final AppraisalReportRepository appraisalReportRepository;
  private final AppraisalReportMapper appraisalReportMapper;
  private final ApproveRepository approveRepository;
  private final ApprovalMapper approvalMapper;
  private final ApprovalProcessService approvalProcessService;
  private final ApplicationRepository applicationRepository;

  public ApprovalProcessResponse request(AppraisalReportRequest appraisalReportRequest) {
    return approvalProcessService.create(
        appraisalReportRequest,
        appraisalReportRequest.getApplication(),
        ProcessType.CREATE_APPRAISAL_REPORT);
  }

  public AppraisalReportResponse create(AppraisalReportRequest appraisalReportRequest) {
    AppraisalReport appraisalReport = appraisalReportMapper.toEntity(appraisalReportRequest);
    var application =
        applicationRepository
            .findById(UUID.fromString(appraisalReportRequest.getApplication().getId()))
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Application not found: "
                            + appraisalReportRequest.getApplication().getId()));
    appraisalReport.setApplication(application);
    return appraisalReportMapper.toResponse(appraisalReportRepository.save(appraisalReport));
  }

  public AppraisalReportResponse update(UUID id, AppraisalReportRequest appraisalReportRequest) {
    AppraisalReport appraisalReport =
        appraisalReportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("AppraisalReport not found"));
    appraisalReportMapper.updateEntity(appraisalReportRequest, appraisalReport);
    return appraisalReportMapper.toResponse(appraisalReportRepository.save(appraisalReport));
  }

  public void delete(UUID id) {
    appraisalReportRepository.deleteById(id);
  }

  public AppraisalReportResponse getById(UUID id) {
    AppraisalReport appraisalReport =
        appraisalReportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("AppraisalReport not found"));
    return appraisalReportMapper.toResponse(appraisalReport);
  }

  public List<AppraisalReportResponse> getAll() {
    return appraisalReportRepository.findAll().stream()
        .map(appraisalReportMapper::toResponse)
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
