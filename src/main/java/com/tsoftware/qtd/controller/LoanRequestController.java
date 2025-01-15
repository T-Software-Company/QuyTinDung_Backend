package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.service.LoanRequestService;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan-requests")
@RequiredArgsConstructor
public class LoanRequestController {

  private final LoanRequestService loanRequestService;

  @PostMapping
  public ResponseEntity<?> create(
      @RequestBody @Valid LoanRequestRequest loanRequestRequest,
      @Valid @IsUUID @RequestParam String applicationId) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Created",
            loanRequestService.request(loanRequestRequest, UUID.fromString(applicationId))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> update(
      @PathVariable UUID id, @RequestBody LoanRequestRequest loanRequestRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", loanRequestService.update(id, loanRequestRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    loanRequestService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", loanRequestService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<LoanRequestResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", loanRequestService.getAll()));
  }
}
