package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.appraisal.AppraisalReportRequest;
import com.tsoftware.qtd.dto.appraisal.AppraisalReportResponse;
import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.service.AppraisalReportService;
import com.tsoftware.qtd.util.ValidationUtils;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appraisal-reports")
@RequiredArgsConstructor
public class AppraisalReportController {

  private final AppraisalReportService appraisalReportService;

  @PostMapping
  @WorkflowEngine(
      step = WorkflowStep.CREATE_APPRAISAL_REPORT,
      action = WorkflowEngine.WorkflowAction.CREATE)
  public ResponseEntity<?> create(
      @RequestBody @Valid AppraisalReportRequest appraisalReportRequest,
      @RequestParam @TargetId @Valid @IsUUID String applicationId) {
    ValidationUtils.validateEqual(applicationId, appraisalReportRequest.getApplication().getId());
    return ResponseEntity.ok(appraisalReportService.request(appraisalReportRequest));
  }

  @WorkflowEngine(action = WorkflowEngine.WorkflowAction.UPDATE)
  @PutMapping("/{id}")
  public ResponseEntity<?> updateRequest(
      @PathVariable @Valid @IsUUID @TransactionId String id,
      @RequestBody @Valid AppraisalReportRequest appraisalReportRequest) {
    return ResponseEntity.ok(
        appraisalReportService.updateRequest(UUID.fromString(id), appraisalReportRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<AppraisalReportResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(200, "Fetched", appraisalReportService.getById(id)));
  }

  @PostMapping("/{id}/add-approve")
  public ResponseEntity<ApiResponse<List<ApprovalResponse>>> addApprove(
      @RequestBody List<UUID> approverIds, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Added", appraisalReportService.addApprove(id, approverIds)));
  }

  @PostMapping("/{id}/remove-approve")
  public ResponseEntity<ApiResponse<Void>> removeApprove(
      @RequestBody List<UUID> approverIds, @PathVariable UUID id) {
    appraisalReportService.removeApprove(id, approverIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }
}
