package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.employee.EmployeeCreateRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/employees")
public class EmployeeController {

  EmployeeService employeeService;

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Object>> create(
      @RequestBody @Valid EmployeeCreateRequest request) {
    employeeService.createEmployee(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.builder().message("successfully").build());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployees() {
    return ResponseEntity.ok(
        ApiResponse.<List<EmployeeResponse>>builder()
            .result(employeeService.getEmployees())
            .build());
  }

  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<EmployeeResponse>> getProfile() {
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder().result(employeeService.getProfile()).build());
  }

  @PutMapping("client/my-employee")
  public ResponseEntity<ApiResponse<Void>> updateMyProfile(
      @RequestBody @Valid ProfileRequest request) {
    employeeService.updateProfile(request);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder().message("Profile updated successfully").build());
  }

  @PutMapping("/{userId}")
  public ResponseEntity<ApiResponse<Void>> updateProfileByUserId(
      @PathVariable String userId, @RequestBody @Valid EmployeeCreateRequest request) {
    employeeService.updateEmployee(userId, request);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder().message("Profile updated successfully").build());
  }

  @PutMapping("client/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPasswordForCurrentUser(
      @RequestBody Map<String, Object> request) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    String newPassword = (String) request.get("newPassword");
    employeeService.resetPassword(userId, newPassword);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder().message("Password reset successfully").build());
  }

  @PutMapping("/{userId}/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPasswordForUserByAdmin(
      @PathVariable String userId, @RequestBody Map<String, Object> request) {
    String newPassword = (String) request.get("newPassword");
    employeeService.resetPassword(userId, newPassword);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder().message("Password reset successfully").build());
  }

  @PutMapping("/{userId}/activate")
  public ResponseEntity<ApiResponse<Void>> activateUser(@PathVariable String userId) {
    employeeService.activateUser(userId);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder().message("User activated successfully").build());
  }

  @PutMapping("/{userId}/deactivate")
  public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable String userId) {
    employeeService.deactivateUser(userId);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder().message("User deactivated successfully").build());
  }
}
