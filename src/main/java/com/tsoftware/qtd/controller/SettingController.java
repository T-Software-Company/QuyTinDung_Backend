package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.setting.ApprovalSettingRequest;
import com.tsoftware.qtd.entity.ApprovalSetting;
import com.tsoftware.qtd.service.ApprovalSettingService;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingController {
  private final ApprovalSettingService approvalSettingService;

  @GetMapping("/approval-settings")
  public ResponseEntity<?> getAllApprovalSettings(
      @Filter Specification<ApprovalSetting> spec, Pageable pageable) {
    return ResponseEntity.ok(approvalSettingService.getAll(spec, pageable));
  }

  @PostMapping("/approval-settings")
  public ResponseEntity<?> createApproveSetting(
      @RequestBody @Valid ApprovalSettingRequest approvalSettingRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Created",
            approvalSettingService.create(approvalSettingRequest)));
  }

  @PutMapping("/approval-settings/{id}")
  public ResponseEntity<?> updateApproveSetting(
      @RequestBody @Valid ApprovalSettingRequest approvalSettingRequest,
      @Valid @IsUUID @PathVariable String id) {
    return ResponseEntity.ok(
        approvalSettingService.update(approvalSettingRequest, UUID.fromString(id)));
  }

  @DeleteMapping("/approval-settings/{id}")
  public ResponseEntity<?> deleteApproveSetting(@Valid @IsUUID @PathVariable String id) {
    approvalSettingService.delete(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }
}
