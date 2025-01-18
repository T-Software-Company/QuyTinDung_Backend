package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.entity.WorkflowTransaction;
import com.tsoftware.qtd.service.WorkflowTransactionService;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow-transactions")
@RequiredArgsConstructor
public class WorkflowTransactionController {
  private final WorkflowTransactionService workflowTransactionService;

  @WorkflowAPI(action = WorkflowAPI.WorkflowAction.APPROVE)
  @PostMapping("{id}/approve")
  public ResponseEntity<?> approveRequest(
      @PathVariable @Valid @TransactionId @IsUUID String id,
      @Valid @IsEnum(enumClass = ApproveStatus.class) @RequestParam String status) {
    return ResponseEntity.ok(
        workflowTransactionService.approve(UUID.fromString(id), ApproveStatus.valueOf(status)));
  }

  @GetMapping
  public ResponseEntity<?> getAll(
      @Filter Specification<WorkflowTransaction> spec, Pageable pageable) {
    return ResponseEntity.ok(workflowTransactionService.getAll(spec, pageable));
  }
}
