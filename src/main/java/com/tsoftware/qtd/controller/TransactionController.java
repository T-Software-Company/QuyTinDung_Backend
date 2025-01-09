package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.dto.transaction.ApproveRequest;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
  final TransactionService transactionService;

  @WorkflowAPI(step = "")
  @PostMapping("/approve")
  public ResponseEntity<?> approveRequest(@RequestBody ApproveRequest approveRequest) {
    return ResponseEntity.ok(transactionService.approve(approveRequest));
  }
}
