package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.service.DisbursementService;
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
@RequestMapping("/disbursements")
@RequiredArgsConstructor
public class DisbursementController {

  private final DisbursementService disbursementService;

  @PostMapping
  public ResponseEntity<ApiResponse<DisbursementDTO>> create(
      @RequestBody DisbursementDTO disbursementDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", disbursementService.create(disbursementDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<DisbursementDTO>> update(
      @PathVariable UUID id, @RequestBody DisbursementDTO disbursementDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", disbursementService.update(id, disbursementDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    disbursementService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<DisbursementDTO>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", disbursementService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<DisbursementDTO>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", disbursementService.getAll()));
  }
}
