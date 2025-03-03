package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.service.LoanRequestService;
import com.tsoftware.qtd.util.ValidationUtils;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-requests")
@RequiredArgsConstructor
public class LoanRequestController {

  private final LoanRequestService loanRequestService;

  @PostMapping
  @WorkflowEngine(
      step = WorkflowStep.CREATE_LOAN_REQUEST,
      action = WorkflowEngine.WorkflowAction.CREATE)
  public ResponseEntity<?> create(
      @RequestBody @Valid LoanRequestRequest loanRequestRequest,
      @Valid @IsUUID @TargetId @RequestParam String applicationId) {
    ValidationUtils.validateEqual(applicationId, loanRequestRequest.getApplication().getId());
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.CREATED.value(), "Created", loanRequestService.request(loanRequestRequest)));
  }

  @WorkflowEngine(action = WorkflowEngine.WorkflowAction.UPDATE)
  @PutMapping("/{id}")
  public ResponseEntity<?> updateRequest(
      @PathVariable @Valid @IsUUID @TransactionId String id,
      @Valid @RequestBody LoanRequestRequest loanRequestRequest) {
    return ResponseEntity.ok(
        loanRequestService.updateRequest(UUID.fromString(id), loanRequestRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", loanRequestService.getById(id)));
  }
}
