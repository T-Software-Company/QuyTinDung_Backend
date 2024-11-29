package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.service.AssetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assets")
public class AssetController {

  @Autowired private AssetService assetService;

  @PostMapping
  public ResponseEntity<ApiResponse<AssetResponse>> create(
      @RequestBody AssetRequest assetRequest, @RequestParam Long creditId) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Created",
                assetService.create(assetRequest, creditId)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetResponse>> update(
      @PathVariable Long id, @RequestBody AssetRequest assetRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Updated", assetService.update(id, assetRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    assetService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", assetService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<AssetResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched All", assetService.getAll()));
  }
}
