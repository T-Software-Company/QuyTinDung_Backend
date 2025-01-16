package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.service.LoanRequestService;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-requests")
@RequiredArgsConstructor
public class LoanRequestController {

  private final LoanRequestService loanRequestService;

  @PostMapping
  @WorkflowAPI(step = "loan-request")
  public ResponseEntity<?> create(
      @RequestBody @Valid LoanRequestRequest loanRequestRequest,
      @Valid @IsUUID @TargetId @RequestParam String applicationId) {
    if (!applicationId.equals(loanRequestRequest.getApplication().getId())) {
      throw new CommonException(ErrorType.CHECKSUM_INVALID, applicationId);
    }
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.CREATED.value(), "Created", loanRequestService.request(loanRequestRequest)));
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
