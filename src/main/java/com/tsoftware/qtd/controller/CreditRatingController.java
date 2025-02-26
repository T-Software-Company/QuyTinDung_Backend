package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.application.CreditRatingRequest;
import com.tsoftware.qtd.dto.application.CreditResponse;
import com.tsoftware.qtd.service.CreditRatingService;
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
@RequestMapping("/credit-ratings")
@RequiredArgsConstructor
public class CreditRatingController {

  private final CreditRatingService creditRatingService;

  @PostMapping
  @WorkflowEngine(
      step = WorkflowStep.CREATE_CREDIT_RATING,
      action = WorkflowEngine.WorkflowAction.CREATE)
  public ResponseEntity<ApiResponse<CreditResponse>> create(
      @RequestBody @Valid CreditRatingRequest request,
      @RequestParam @TargetId @Valid @IsUUID String applicationId) {
    ValidationUtils.validateEqual(applicationId, request.getApplication().getId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Created",
                creditRatingService.create(request, UUID.fromString(applicationId))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditResponse>> update(
      @PathVariable UUID id, @RequestBody @Valid CreditRatingRequest request) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Updated", creditRatingService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    creditRatingService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", creditRatingService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CreditResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched All", creditRatingService.getAll()));
  }
}
