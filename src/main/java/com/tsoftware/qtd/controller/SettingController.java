package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.setting.ApproveSettingRequest;
import com.tsoftware.qtd.service.ApproveSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingController {
  private final ApproveSettingService approveSettingService;

  @GetMapping
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok("ok");
  }

  @PostMapping
  public ResponseEntity<?> createApproveSetting(
      @RequestBody @Valid ApproveSettingRequest approveSettingRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", approveSettingService.create(approveSettingRequest)));
  }
}
