package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.entity.EmployeeNotification;
import com.tsoftware.qtd.service.EmployeeNotificationService;
import com.tsoftware.qtd.util.RequestUtil;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<?> getNotification(
      @Filter Specification<EmployeeNotification> spec, Pageable pageable) {
    var userId = RequestUtil.getUserId();
    Specification<EmployeeNotification> userIdSpec =
        (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("employee").get("userId"), userId);
    var finalSpec = spec == null ? userIdSpec : spec.and(userIdSpec);
    return ResponseEntity.ok(employeeNotificationService.getAll(finalSpec, pageable));
  }

  @PostMapping("/{id}/read")
  public ResponseEntity<?> readNotification(@PathVariable @IsUUID @Valid String id) {
    employeeNotificationService.read(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }

  @PostMapping("/{id}/unread")
  public ResponseEntity<?> unReadNotification(@PathVariable @IsUUID @Valid String id) {
    employeeNotificationService.unRead(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }

  @PostMapping("/read")
  public ResponseEntity<?> readNotification(@RequestBody @Valid List<@IsUUID String> ids) {
    employeeNotificationService.read(ids.stream().map(UUID::fromString).toList());
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }

  @PostMapping("/unread")
  public ResponseEntity<?> unReadNotification(@RequestBody @Valid List<@IsUUID String> ids) {
    employeeNotificationService.unRead(ids.stream().map(UUID::fromString).toList());
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }
}
