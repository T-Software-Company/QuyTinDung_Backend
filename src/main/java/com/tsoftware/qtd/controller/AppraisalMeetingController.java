package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.appraisal.AppraisalMeetingRequest;
import com.tsoftware.qtd.dto.appraisal.AppraisalMeetingResponse;
import com.tsoftware.qtd.entity.AppraisalMeeting;
import com.tsoftware.qtd.service.AppraisalMeetingService;
import com.tsoftware.qtd.util.ValidationUtils;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appraisal-meetings")
@RequiredArgsConstructor
public class AppraisalMeetingController {
  private final AppraisalMeetingService appraisalMeetingService;

  @WorkflowEngine(
      step = WorkflowStep.CREATE_APPRAISAL_MEETING,
      action = WorkflowEngine.WorkflowAction.CREATE)
  @PostMapping
  public ResponseEntity<ApiResponse<AppraisalMeetingResponse>> create(
      @RequestBody @Valid AppraisalMeetingRequest request,
      @Valid @TargetId @IsUUID @RequestParam String applicationId) {
    ValidationUtils.validateEqual(request.getApplication().getId(), applicationId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Created",
                appraisalMeetingService.create(request, UUID.fromString(applicationId))));
  }

  @PostMapping("/{id}/add-participants")
  public ResponseEntity<ApiResponse<Void>> addParticipants(
      @PathVariable UUID id, @RequestBody List<UUID> participantIds) {
    appraisalMeetingService.addParticipants(id, participantIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  @DeleteMapping("/{id}/remove-participants")
  public ResponseEntity<ApiResponse<Void>> removeParticipants(
      @PathVariable UUID id, @RequestBody List<UUID> participantIds) {
    appraisalMeetingService.removeParticipants(id, participantIds);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Removed", null));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<AppraisalMeetingResponse>> update(
      @PathVariable UUID id, @RequestBody AppraisalMeetingRequest request) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Updated", appraisalMeetingService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    appraisalMeetingService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<AppraisalMeetingResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", appraisalMeetingService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<AppraisalMeetingResponse>>> getAll(
      @Filter Specification<AppraisalMeeting> spec, Pageable page) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", appraisalMeetingService.getAll(spec, page)));
  }
}
