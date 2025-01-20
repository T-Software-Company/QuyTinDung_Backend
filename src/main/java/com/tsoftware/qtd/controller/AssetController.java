package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.service.AssetService;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

  private final AssetService assetService;

  @PostMapping
  @WorkflowAPI(step = WorkflowStep.ADD_ASSET_COLLATERAL, action = WorkflowAPI.WorkflowAction.CREATE)
  public ResponseEntity<?> create(
      @RequestBody List<@Valid AssetRequest> assetsRequest,
      @RequestParam @Valid @IsUUID String applicationId) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Created",
                assetService.create(assetsRequest, UUID.fromString(applicationId))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetResponse>> update(
      @PathVariable UUID id, @RequestBody AssetRequest assetRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Updated", assetService.update(id, assetRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    assetService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<AssetResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", assetService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<AssetResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched All", assetService.getAll()));
  }
}
