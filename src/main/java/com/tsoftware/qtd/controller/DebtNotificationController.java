package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDTO;
import com.tsoftware.qtd.service.DebtNotificationService;
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
@RequestMapping("/debt-notifications")
@RequiredArgsConstructor
public class DebtNotificationController {

  private final DebtNotificationService debtnotificationService;

  @PostMapping
  public ResponseEntity<ApiResponse<DebtNotificationDTO>> create(
      @RequestBody DebtNotificationDTO debtnotificationDTO) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", debtnotificationService.create(debtnotificationDTO)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<DebtNotificationDTO>> update(
      @PathVariable UUID id, @RequestBody DebtNotificationDTO debtnotificationDTO) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", debtnotificationService.update(id, debtnotificationDTO)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    debtnotificationService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<DebtNotificationDTO>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", debtnotificationService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<DebtNotificationDTO>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", debtnotificationService.getAll()));
  }
}
