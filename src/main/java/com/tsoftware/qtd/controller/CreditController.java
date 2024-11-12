package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.credit.CreditDto;
import com.tsoftware.qtd.service.CreditService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

  @Autowired private CreditService creditService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreditDto>> create(@RequestBody CreditDto creditDto) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Created", creditService.create(creditDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditDto>> update(
      @PathVariable Long id, @RequestBody CreditDto creditDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", creditService.update(id, creditDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    creditService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", creditService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CreditDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", creditService.getAll()));
  }
}
