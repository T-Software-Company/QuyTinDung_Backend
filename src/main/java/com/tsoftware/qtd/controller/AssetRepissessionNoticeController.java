package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.AssetRepissessionNoticeDto;
import com.tsoftware.qtd.response.ApiResponse;
import com.tsoftware.qtd.service.AssetRepissessionNoticeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assetrepissessionnotices")
public class AssetRepissessionNoticeController {

  @Autowired private AssetRepissessionNoticeService assetrepissessionnoticeService;

  @PostMapping
  public ResponseEntity<ApiResponse<AssetRepissessionNoticeDto>> create(
      @RequestBody AssetRepissessionNoticeDto assetrepissessionnoticeDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Created", assetrepissessionnoticeService.create(assetrepissessionnoticeDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetRepissessionNoticeDto>> update(
      @PathVariable Long id, @RequestBody AssetRepissessionNoticeDto assetrepissessionnoticeDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000,
            "Updated",
            assetrepissessionnoticeService.update(id, assetrepissessionnoticeDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    assetrepissessionnoticeService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetRepissessionNoticeDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", assetrepissessionnoticeService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<AssetRepissessionNoticeDto>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", assetrepissessionnoticeService.getAll()));
  }
}
