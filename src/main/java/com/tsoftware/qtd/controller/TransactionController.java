package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.service.TransactionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
@RequiredArgsConstructor
public class TransactionController {
  final TransactionService transactionService;

  @PostMapping("/approve/{id}")
  public ResponseEntity<?> approveRequest(@PathVariable UUID id) {
    return ResponseEntity.ok(transactionService.approve(id));
  }
}
