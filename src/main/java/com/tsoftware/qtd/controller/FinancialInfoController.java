package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.service.FinancialInfoService;
import com.tsoftware.qtd.util.ValidationUtils;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financial-infos")
@RequiredArgsConstructor
public class FinancialInfoController {
  private final FinancialInfoService financialinfoService;

  @PostMapping
  @WorkflowAPI(
      step = WorkflowStep.CREATE_FINANCIAL_INFO,
      action = WorkflowAPI.WorkflowAction.CREATE)
  public ResponseEntity<?> createFinancialInfo(
      @Valid @RequestBody FinancialInfoRequest financialInfoRequest,
      @Valid @RequestParam @IsUUID @TargetId String applicationId) {
    ValidationUtils.validateEqual(financialInfoRequest.getApplication().getId(), applicationId);
    return ResponseEntity.ok(financialinfoService.request(financialInfoRequest));
  }
}
