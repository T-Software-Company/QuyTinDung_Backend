package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowEngine;
import com.tsoftware.qtd.dto.approval.ApprovalRequest;
import com.tsoftware.qtd.entity.ApprovalProcess;
import com.tsoftware.qtd.service.ApprovalProcessService;
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
@RequestMapping("/approval-process")
@RequiredArgsConstructor
public class ApprovalProcessController {
  private final ApprovalProcessService approvalProcessService;

  @WorkflowEngine(action = WorkflowEngine.WorkflowAction.APPROVE)
  @PostMapping("/{id}/approve")
  public ResponseEntity<?> approve(
      @PathVariable @Valid @TransactionId @IsUUID String id,
      @Valid @RequestBody ApprovalRequest approvalRequest) {
    return ResponseEntity.ok(approvalProcessService.approve(UUID.fromString(id), approvalRequest));
  }

  @GetMapping
  public ResponseEntity<?> getAll(@Filter Specification<ApprovalProcess> spec, Pageable pageable) {
    return ResponseEntity.ok(approvalProcessService.getAll(spec, pageable));
  }
}
