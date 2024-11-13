package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingDto;
import com.tsoftware.qtd.service.ValuationMeetingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valuationmeetings")
public class ValuationMeetingController {

  @Autowired private ValuationMeetingService valuationmeetingService;

  //    @PostMapping
  //    public ResponseEntity<ApiResponse<ValuationMeetingDto>> create(@RequestBody
  // ValuationMeetingDto valuationmeetingDto) {
  //        return ResponseEntity.ok(new ApiResponse<>(1000, "Created",
  // valuationmeetingService.create(valuationmeetingDto)));
  //    }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationMeetingDto>> update(
      @PathVariable Long id, @RequestBody ValuationMeetingDto valuationmeetingDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", valuationmeetingService.update(id, valuationmeetingDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    valuationmeetingService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationMeetingDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", valuationmeetingService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ValuationMeetingDto>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", valuationmeetingService.getAll()));
  }
}
