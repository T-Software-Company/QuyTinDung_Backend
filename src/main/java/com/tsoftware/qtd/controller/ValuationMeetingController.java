package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.service.ValuationMeetingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valuation-meetings")
public class ValuationMeetingController {

  @Autowired private ValuationMeetingService valuationMeetingService;

  @PostMapping("/{id}/add-participants")
  public ResponseEntity<ApiResponse<Void>> addParticipants(
      @PathVariable Long id, @RequestBody List<Long> participantIds) {
    valuationMeetingService.addParticipants(id, participantIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  @DeleteMapping("/{id}/remove-participants")
  public ResponseEntity<ApiResponse<Void>> RemoveParticipants(
      @PathVariable Long id, @RequestBody List<Long> participantIds) {
    valuationMeetingService.removeParticipants(id, participantIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  //    @PostMapping
  //    public ResponseEntity<ApiResponse<ValuationMeetingResponse>> create(@RequestBody
  // ValuationMeetingRequest valuationmeetingDto) {
  //        return ResponseEntity.ok(new ApiResponse<>(1000, "Created",
  // valuationMeetingService.create(valuationmeetingDto)));
  //    }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> update(
      @PathVariable Long id, @RequestBody ValuationMeetingRequest valuationmeetingRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", valuationMeetingService.update(id, valuationmeetingRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    valuationMeetingService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", valuationMeetingService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ValuationMeetingResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", valuationMeetingService.getAll()));
  }
}
