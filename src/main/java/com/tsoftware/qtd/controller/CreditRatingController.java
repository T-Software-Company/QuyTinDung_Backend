package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.application.CreditRatingRequest;
import com.tsoftware.qtd.dto.application.CreditRatingResponse;
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
  public ResponseEntity<ApiResponse<CreditRatingResponse>> create(
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
  public ResponseEntity<ApiResponse<CreditRatingResponse>> update(
      @PathVariable @Valid @IsUUID String id, @RequestBody @Valid CreditRatingRequest request) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(),
            "Updated",
            creditRatingService.update(UUID.fromString(id), request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @Valid @IsUUID String id) {
    creditRatingService.delete(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditRatingResponse>> getById(
      @PathVariable @Valid @IsUUID String id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched", creditRatingService.getById(UUID.fromString(id))));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CreditRatingResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched All", creditRatingService.getAll()));
  }
}
