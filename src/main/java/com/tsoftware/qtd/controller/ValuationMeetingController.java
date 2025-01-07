package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.service.ValuationMeetingService;
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
@RequestMapping("/valuation-meetings")
@RequiredArgsConstructor
public class ValuationMeetingController {

  private final ValuationMeetingService valuationMeetingService;

  @PostMapping("/{id}/add-participants")
  public ResponseEntity<ApiResponse<Void>> addParticipants(
      @PathVariable UUID id, @RequestBody List<UUID> participantIds) {
    valuationMeetingService.addParticipants(id, participantIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  @DeleteMapping("/{id}/remove-participants")
  public ResponseEntity<ApiResponse<Void>> RemoveParticipants(
      @PathVariable UUID id, @RequestBody List<UUID> participantIds) {
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
      @PathVariable UUID id, @RequestBody ValuationMeetingRequest valuationmeetingRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", valuationMeetingService.update(id, valuationmeetingRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    valuationMeetingService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", valuationMeetingService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ValuationMeetingResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", valuationMeetingService.getAll()));
  }
}
