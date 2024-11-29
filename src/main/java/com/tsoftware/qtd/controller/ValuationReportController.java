package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.service.ValuationReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valuation-reports")
public class ValuationReportController {

  @Autowired private ValuationReportService valuationreportService;

  @PostMapping
  public ResponseEntity<ApiResponse<ValuationReportResponse>> create(
      @RequestBody ValuationReportRequest valuationreportRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", valuationreportService.create(valuationreportRequest)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportResponse>> update(
      @PathVariable Long id, @RequestBody ValuationReportRequest valuationreportRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", valuationreportService.update(id, valuationreportRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    valuationreportService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", valuationreportService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ValuationReportResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", valuationreportService.getAll()));
  }

  @PostMapping("/{id}/add-approve")
  public ResponseEntity<ApiResponse<List<ApproveResponse>>> addApprove(
      @RequestBody List<Long> approverIds, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Added", valuationreportService.addApprove(id, approverIds)));
  }

  @PostMapping("/{id}/remove-approve")
  public ResponseEntity<ApiResponse<Void>> removeApprove(
      @RequestBody List<Long> approverIds, @PathVariable Long id) {
    valuationreportService.removeApprove(id, approverIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }
}
