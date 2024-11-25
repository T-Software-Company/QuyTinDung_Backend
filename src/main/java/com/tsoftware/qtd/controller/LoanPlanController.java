package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.credit.LoanPlanRequest;
import com.tsoftware.qtd.dto.credit.LoanPlanResponse;
import com.tsoftware.qtd.service.LoanPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-plans")
public class LoanPlanController {

  @Autowired private LoanPlanService loanplanService;

  @PostMapping
  public ResponseEntity<ApiResponse<LoanPlanResponse>> create(
      @RequestBody LoanPlanRequest loanPlanRequest, @PathVariable Long creditId) throws Exception {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", loanplanService.create(loanPlanRequest, creditId)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> update(
      @PathVariable Long id, @RequestBody LoanPlanRequest loanPlanRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", loanplanService.update(id, loanPlanRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    loanplanService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", loanplanService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<LoanPlanResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", loanplanService.getAll()));
  }
}
