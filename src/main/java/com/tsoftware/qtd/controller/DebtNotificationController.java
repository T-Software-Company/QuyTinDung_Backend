package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import com.tsoftware.qtd.service.DebtNotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
      @PathVariable Long id, @RequestBody DebtNotificationDto debtnotificationDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Updated", debtnotificationService.update(id, debtnotificationDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    debtnotificationService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<DebtNotificationDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched", debtnotificationService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<DebtNotificationDto>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", debtnotificationService.getAll()));
  }
}
