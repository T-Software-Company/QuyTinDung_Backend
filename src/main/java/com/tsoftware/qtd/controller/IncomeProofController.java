package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.credit.IncomeProofDto;
import com.tsoftware.qtd.service.IncomeProofService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/income-proofs")
public class IncomeProofController {

  @Autowired private IncomeProofService incomeproofService;

  @PostMapping
  public ResponseEntity<ApiResponse<IncomeProofDto>> create(
      @RequestBody IncomeProofDto incomeproofDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", incomeproofService.create(incomeproofDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<IncomeProofDto>> update(
      @PathVariable Long id, @RequestBody IncomeProofDto incomeproofDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", incomeproofService.update(id, incomeproofDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    incomeproofService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<IncomeProofDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", incomeproofService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<IncomeProofDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", incomeproofService.getAll()));
  }
}
