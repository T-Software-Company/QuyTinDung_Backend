package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.entity.ApprovalProcess;
import com.tsoftware.qtd.service.ApprovalProcessService;
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
@RequestMapping("/approval-process")
@RequiredArgsConstructor
public class ApprovalProcessController {
  private final ApprovalProcessService approvalProcessService;

  @WorkflowAPI(action = WorkflowAPI.WorkflowAction.APPROVE)
  @PostMapping("{id}/approve")
  public ResponseEntity<?> approveRequest(
      @PathVariable @Valid @TransactionId @IsUUID String id,
      @Valid @IsEnum(enumClass = ActionStatus.class) @RequestParam String status) {
    return ResponseEntity.ok(
        approvalProcessService.approve(UUID.fromString(id), ActionStatus.valueOf(status)));
  }

  @GetMapping
  public ResponseEntity<?> getAll(@Filter Specification<ApprovalProcess> spec, Pageable pageable) {
    return ResponseEntity.ok(approvalProcessService.getAll(spec, pageable));
  }
}
