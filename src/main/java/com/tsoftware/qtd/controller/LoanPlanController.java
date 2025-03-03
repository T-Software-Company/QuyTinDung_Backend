package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.service.LoanPlanService;
import com.tsoftware.qtd.util.ValidationUtils;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-plans")
@RequiredArgsConstructor
public class LoanPlanController {

  private final LoanPlanService loanplanService;

  @PostMapping
  @WorkflowEngine(
      step = WorkflowStep.CREATE_LOAN_PLAN,
      action = WorkflowEngine.WorkflowAction.CREATE)
  public ResponseEntity<?> request(
      @RequestBody @Valid LoanPlanRequest loanPlanRequest,
      @Valid @RequestParam @IsUUID @TargetId String applicationId)
      throws Exception {
    ValidationUtils.validateEqual(applicationId, loanPlanRequest.getApplication().getId());
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.CREATED.value(), "Created", loanplanService.request(loanPlanRequest)));
  }

  @WorkflowEngine(action = WorkflowEngine.WorkflowAction.UPDATE)
  @PutMapping("/{id}")
  public ResponseEntity<?> updateRequest(
      @PathVariable @Valid @IsUUID @TransactionId String id,
      @Valid @RequestBody LoanPlanRequest loanPlanRequest) {
    return ResponseEntity.ok(loanplanService.updateRequest(UUID.fromString(id), loanPlanRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", loanplanService.getById(id)));
  }
}
