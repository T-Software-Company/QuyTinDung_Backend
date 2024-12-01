package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.credit.DisbursementDto;
import com.tsoftware.qtd.service.DisbursementService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disbursements")
@RequiredArgsConstructor
public class DisbursementController {

  private final DisbursementService disbursementService;

  @PostMapping
  public ResponseEntity<ApiResponse<DisbursementDto>> create(
      @RequestBody DisbursementDto disbursementDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", disbursementService.create(disbursementDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<DisbursementDto>> update(
      @PathVariable UUID id, @RequestBody DisbursementDto disbursementDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", disbursementService.update(id, disbursementDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    disbursementService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<DisbursementDto>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", disbursementService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<DisbursementDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", disbursementService.getAll()));
  }
}
