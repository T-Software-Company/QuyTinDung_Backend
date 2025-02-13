package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.entity.EmployeeNotification;
import com.tsoftware.qtd.service.EmployeeNotificationService;
import com.tsoftware.qtd.util.RequestUtil;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee-notifications")
@RequiredArgsConstructor
public class EmployeeNotificationController {

  private final EmployeeNotificationService employeeNotificationService;

  @GetMapping
  public ResponseEntity<?> getAll(
      @Filter Specification<EmployeeNotification> spec, Pageable pageable) {
    return ResponseEntity.ok(employeeNotificationService.getAll(spec, pageable));
  }

  @GetMapping("/{employeeId}")
  public ResponseEntity<?> getNotification(
      @Valid @PathVariable @IsUUID String employeeId, Pageable pageable) {
    return ResponseEntity.ok(
        employeeNotificationService.getByEmployeeId(UUID.fromString(employeeId), pageable));
  }

  @GetMapping("/my-notifications")
  public ResponseEntity<?> getNotification(Pageable pageable) {
    var employeeId = RequestUtil.getUserId();
    return ResponseEntity.ok(employeeNotificationService.getByUserId(employeeId, pageable));
  }
}
