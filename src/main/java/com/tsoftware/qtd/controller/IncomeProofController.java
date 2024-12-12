package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.credit.IncomeProofDto;
import com.tsoftware.qtd.service.IncomeProofService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/income-proofs")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IncomeProofController {

  private final IncomeProofService incomeproofService;

  @PostMapping
  public ResponseEntity<ApiResponse<IncomeProofDto>> create(
      @RequestBody IncomeProofDto incomeproofDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", incomeproofService.create(incomeproofDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<IncomeProofDto>> update(
      @PathVariable UUID id, @RequestBody IncomeProofDto incomeproofDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", incomeproofService.update(id, incomeproofDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    incomeproofService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<IncomeProofDto>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", incomeproofService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<IncomeProofDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", incomeproofService.getAll()));
  }
}
