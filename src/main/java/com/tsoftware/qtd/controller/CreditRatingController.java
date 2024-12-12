package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.credit.CreditRatingDto;
import com.tsoftware.qtd.service.CreditRatingService;
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
@RequestMapping("/credit-ratings")
@RequiredArgsConstructor
public class CreditRatingController {

  private final CreditRatingService creditratingService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreditRatingDto>> create(
      @RequestBody CreditRatingDto creditratingDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", creditratingService.create(creditratingDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditRatingDto>> update(
      @PathVariable UUID id, @RequestBody CreditRatingDto creditratingDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", creditratingService.update(id, creditratingDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    creditratingService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditRatingDto>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", creditratingService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CreditRatingDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", creditratingService.getAll()));
  }
}
