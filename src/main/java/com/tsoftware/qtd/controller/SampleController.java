package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.request.CreateCustomerRequest;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {
  final TransactionService transactionService;

  @PostMapping("/customer/submit")
  public ResponseEntity<?> createRequest(@RequestBody CreateCustomerRequest request) {
    return ResponseEntity.ok(
        ApiResponse.builder()
            .code(1)
            .message("Create Customer")
            .result(transactionService.createCustomerRequest(request))
            .build());
  }

  @PostMapping("/customer/approve/{id}")
  public ResponseEntity<?> approveRequest(@PathVariable Long id) {
    return ResponseEntity.ok(
        ApiResponse.builder()
            .code(1)
            .message("Approve Customer")
            .result(transactionService.approve(id))
            .build());
  }
}
