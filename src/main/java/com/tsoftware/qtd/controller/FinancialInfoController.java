package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.service.FinancialInfoService;
import com.tsoftware.qtd.util.ValidationUtils;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financial-infos")
@RequiredArgsConstructor
public class FinancialInfoController {
  private final FinancialInfoService financialInfoService;

  @PostMapping
  @WorkflowEngine(
      step = WorkflowStep.CREATE_FINANCIAL_INFO,
      action = WorkflowEngine.WorkflowAction.CREATE)
  public ResponseEntity<?> createFinancialInfo(
      @Valid @RequestBody FinancialInfoRequest financialInfoRequest,
      @Valid @RequestParam @IsUUID @TargetId String applicationId) {
    ValidationUtils.validateEqual(financialInfoRequest.getApplication().getId(), applicationId);
    return ResponseEntity.ok(financialInfoService.request(financialInfoRequest));
  }

  @WorkflowEngine(action = WorkflowEngine.WorkflowAction.UPDATE)
  @PutMapping("/{id}")
  public ResponseEntity<?> updateRequest(
      @PathVariable @Valid @IsUUID @TransactionId String id,
      @Valid @RequestBody FinancialInfoRequest financialInfoRequest) {
    return ResponseEntity.ok(
        financialInfoService.updateRequest(UUID.fromString(id), financialInfoRequest));
  }
}
