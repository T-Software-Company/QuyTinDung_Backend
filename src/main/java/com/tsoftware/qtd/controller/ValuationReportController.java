package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.dto.valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.valuation.ValuationReportResponse;
import com.tsoftware.qtd.service.ValuationReportService;
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
@RequestMapping("/valuation-reports")
@RequiredArgsConstructor
public class ValuationReportController {

  private final ValuationReportService valuationReportService;

  @PostMapping
  @WorkflowEngine(
      step = WorkflowStep.CREATE_VALUATION_REPORT,
      action = WorkflowEngine.WorkflowAction.CREATE)
  public ResponseEntity<?> create(
      @RequestBody @Valid ValuationReportRequest valuationReportRequest,
      @RequestParam @TargetId @Valid @IsUUID String applicationId) {
    ValidationUtils.validateEqual(applicationId, valuationReportRequest.getApplication().getId());
    return ResponseEntity.ok(valuationReportService.request(valuationReportRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportResponse>> update(
      @PathVariable UUID id, @RequestBody ValuationReportRequest valuationReportRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", valuationReportService.update(id, valuationReportRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    valuationReportService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", valuationReportService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ValuationReportResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", valuationReportService.getAll()));
  }

  @PostMapping("/{id}/add-approve")
  public ResponseEntity<ApiResponse<List<ApprovalResponse>>> addApprove(
      @RequestBody List<UUID> approverIds, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Added", valuationReportService.addApprove(id, approverIds)));
  }

  @PostMapping("/{id}/remove-approve")
  public ResponseEntity<ApiResponse<Void>> removeApprove(
      @RequestBody List<UUID> approverIds, @PathVariable UUID id) {
    valuationReportService.removeApprove(id, approverIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }
}
