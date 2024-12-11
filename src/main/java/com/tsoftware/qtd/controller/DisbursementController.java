package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.credit.DisbursementDto;
import com.tsoftware.qtd.service.DisbursementService;
import java.util.List;
import java.util.UUID;
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
@RequestMapping("/disbursements")
public class DisbursementController {

  @Autowired private DisbursementService disbursementService;

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
