package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.credit.CreditRatingDto;
import com.tsoftware.qtd.service.CreditRatingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit-ratings")
public class CreditRatingController {

  @Autowired private CreditRatingService creditratingService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreditRatingDto>> create(
      @RequestBody CreditRatingDto creditratingDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", creditratingService.create(creditratingDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditRatingDto>> update(
      @PathVariable Long id, @RequestBody CreditRatingDto creditratingDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", creditratingService.update(id, creditratingDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    creditratingService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditRatingDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", creditratingService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CreditRatingDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", creditratingService.getAll()));
  }
}
