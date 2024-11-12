package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.AssetDto;
import com.tsoftware.qtd.response.ApiResponse;
import com.tsoftware.qtd.service.AssetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assets")
public class AssetController {

  @Autowired private AssetService assetService;

  @PostMapping
  public ResponseEntity<ApiResponse<AssetDto>> create(@RequestBody AssetDto assetDto) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Created", assetService.create(assetDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetDto>> update(
      @PathVariable Long id, @RequestBody AssetDto assetDto) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Updated", assetService.update(id, assetDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    assetService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", assetService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<AssetDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", assetService.getAll()));
  }
}
