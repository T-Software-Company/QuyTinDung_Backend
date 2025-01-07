package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.service.LoanPlanService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan-plans")
@RequiredArgsConstructor
public class LoanPlanController {

  private final LoanPlanService loanplanService;

  @PostMapping
  public ResponseEntity<ApiResponse<LoanPlanResponse>> create(
      @RequestBody LoanPlanDTO loanPlanDTO, @PathVariable UUID creditId) throws Exception {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", loanplanService.create(loanPlanDTO, creditId)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> update(
      @PathVariable UUID id, @RequestBody LoanPlanDTO loanPlanDTO) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", loanplanService.update(id, loanPlanDTO)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    loanplanService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", loanplanService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<LoanPlanResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", loanplanService.getAll()));
  }
}
