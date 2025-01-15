package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.service.WorkflowTransactionService;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
  final WorkflowTransactionService workflowTransactionService;

  @WorkflowAPI(action = WorkflowAPI.Action.APPROVE)
  @PostMapping("{id}/approve")
  public ResponseEntity<?> approveRequest(
      @PathVariable @Valid @IsUUID String id,
      @Valid @IsEnum(enumClass = ApproveStatus.class) @RequestParam String status) {
    return ResponseEntity.ok(
        workflowTransactionService.approve(UUID.fromString(id), ApproveStatus.valueOf(status)));
  }
}
