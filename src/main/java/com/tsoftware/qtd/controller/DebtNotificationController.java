package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import com.tsoftware.qtd.service.DebtNotificationService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DebtNotificationController {

  @Autowired private DebtNotificationService debtnotificationService;

  @PostMapping
  public ResponseEntity<ApiResponse<DebtNotificationDto>> create(
      @RequestBody DebtNotificationDto debtnotificationDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", debtnotificationService.create(debtnotificationDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<DebtNotificationDto>> update(
      @PathVariable UUID id, @RequestBody DebtNotificationDto debtnotificationDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", debtnotificationService.update(id, debtnotificationDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    debtnotificationService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<DebtNotificationDto>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", debtnotificationService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<DebtNotificationDto>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", debtnotificationService.getAll()));
  }
}
