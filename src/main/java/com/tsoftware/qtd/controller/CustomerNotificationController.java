package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.entity.CustomerNotification;
import com.tsoftware.qtd.service.CustomerNotificationService;
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
@RequestMapping("/customer-notifications")
@RequiredArgsConstructor
public class CustomerNotificationController {

  private final CustomerNotificationService customerNotificationService;

  @GetMapping
  public ResponseEntity<?> getAll(
      @Filter Specification<CustomerNotification> spec, Pageable pageable) {
    return ResponseEntity.ok(customerNotificationService.getAll(spec, pageable));
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<?> getNotification(
      @Valid @PathVariable @IsUUID String customerId, Pageable pageable) {
    return ResponseEntity.ok(
        customerNotificationService.getByCustomerId(UUID.fromString(customerId), pageable));
  }

  @GetMapping("/by-notification/{notificationId}")
  public ResponseEntity<?> getByNotificationId(@PathVariable @IsUUID String notificationId) {
    return ResponseEntity.ok(
        customerNotificationService.getByNotificationId(UUID.fromString(notificationId)));
  }

  @GetMapping("/my-notifications")
  public ResponseEntity<?> getNotification(
      @Filter Specification<CustomerNotification> spec, Pageable pageable) {
    var userId = RequestUtil.getUserId();
    Specification<CustomerNotification> userIdSpec =
        (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("customer").get("userId"), userId);
    var finalSpec = spec == null ? userIdSpec : spec.and(userIdSpec);
    return ResponseEntity.ok(customerNotificationService.getAll(finalSpec, pageable));
  }

  @PostMapping("/{id}/read")
  public ResponseEntity<?> readNotification(@PathVariable @IsUUID @Valid String id) {
    customerNotificationService.read(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }

  @PostMapping("/{id}/unread")
  public ResponseEntity<?> unReadNotification(@PathVariable @IsUUID @Valid String id) {
    customerNotificationService.unRead(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }

  @PostMapping("/read")
  public ResponseEntity<?> readNotifications(@RequestBody @Valid List<@IsUUID String> ids) {
    customerNotificationService.read(ids.stream().map(UUID::fromString).toList());
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }

  @PostMapping("/unread")
  public ResponseEntity<?> unReadNotifications(@RequestBody @Valid List<@IsUUID String> ids) {
    customerNotificationService.unRead(ids.stream().map(UUID::fromString).toList());
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", null));
  }
}
