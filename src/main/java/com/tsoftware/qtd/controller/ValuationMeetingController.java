package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.service.ValuationMeetingService;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valuation-meetings")
@RequiredArgsConstructor
public class ValuationMeetingController {

  private final ValuationMeetingService valuationMeetingService;

  @PostMapping
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> create(
      @RequestBody @Valid ValuationMeetingRequest valuationMeetingRequest,
      @Valid @TargetId @IsUUID @RequestParam String applicationId) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000,
            "Created",
            valuationMeetingService.create(
                valuationMeetingRequest, UUID.fromString(applicationId))));
  }

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
