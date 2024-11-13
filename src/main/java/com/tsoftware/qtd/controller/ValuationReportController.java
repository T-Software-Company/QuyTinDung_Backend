package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationReportDto;
import com.tsoftware.qtd.service.ValuationReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valuationreports")
public class ValuationReportController {

  @Autowired private ValuationReportService valuationreportService;

  @PostMapping
  public ResponseEntity<ApiResponse<ValuationReportDto>> create(
      @RequestBody ValuationReportDto valuationreportDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", valuationreportService.create(valuationreportDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportDto>> update(
      @PathVariable Long id, @RequestBody ValuationReportDto valuationreportDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", valuationreportService.update(id, valuationreportDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    valuationreportService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationReportDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", valuationreportService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ValuationReportDto>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", valuationreportService.getAll()));
  }
}
