package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.service.ValuationReportService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/valuation-reports")
@RequiredArgsConstructor
public class ValuationReportController {

  private final ValuationReportService valuationreportService;

  @PostMapping
  public ResponseEntity<ApiResponse<ValuationReportResponse>> create(
      @RequestBody ValuationReportRequest valuationreportRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", valuationreportService.create(valuationreportRequest)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportResponse>> update(
      @PathVariable UUID id, @RequestBody ValuationReportRequest valuationreportRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", valuationreportService.update(id, valuationreportRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    valuationreportService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", valuationreportService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ValuationReportResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", valuationreportService.getAll()));
  }

  @PostMapping("/{id}/add-approve")
  public ResponseEntity<ApiResponse<List<ApprovalResponse>>> addApprove(
      @RequestBody List<UUID> approverIds, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Added", valuationreportService.addApprove(id, approverIds)));
  }

  @PostMapping("/{id}/remove-approve")
  public ResponseEntity<ApiResponse<Void>> removeApprove(
      @RequestBody List<UUID> approverIds, @PathVariable UUID id) {
    valuationreportService.removeApprove(id, approverIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }
}
