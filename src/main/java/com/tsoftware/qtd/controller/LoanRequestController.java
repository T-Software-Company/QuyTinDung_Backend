package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import com.tsoftware.qtd.service.LoanRequestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-requests")
@RequiredArgsConstructor
public class LoanRequestController {

  private final LoanRequestService loanRequestService;

  @PostMapping
  public ResponseEntity<ApiResponse<LoanRequestResponse>> create(
      @RequestBody LoanRequestRequest loanRequestRequest, @RequestParam Long creditId)
      throws Exception {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Created", loanRequestService.create(loanRequestRequest, creditId)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> update(
      @PathVariable Long id, @RequestBody LoanRequestRequest loanRequestRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", loanRequestService.update(id, loanRequestRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    loanRequestService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", loanRequestService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<LoanRequestResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", loanRequestService.getAll()));
  }
}
