package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.service.ApproveService;
import com.tsoftware.qtd.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<ApiResponse<Object>> create(@RequestBody @Valid EmployeeRequest request) {
    employeeService.createEmployee(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ApiResponse.builder().code(HttpStatus.CREATED.value()).message("successfully").build());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployees() {
    return ResponseEntity.ok(
        ApiResponse.<List<EmployeeResponse>>builder()
            .result(employeeService.getEmployees())
            .code(HttpStatus.OK.value())
            .build());
  }

  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<EmployeeResponse>> getProfile() {
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .result(employeeService.getProfile())
            .build());
  }

  @PutMapping("/profile")
  public ResponseEntity<ApiResponse<Void>> updateMyProfile(
      @RequestBody @Valid ProfileRequest request) {
    employeeService.updateProfile(request);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Profile updated successfully")
            .build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> updateEmployee(
      @PathVariable String id, @RequestBody @Valid EmployeeRequest request) {
    employeeService.updateEmployee(id, request);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Profile updated successfully")
            .build());
  }

  @PutMapping("/reset-password")
  public ResponseEntity<ApiResponse<Void>> employeeResetPassword(
      @RequestBody Map<String, Object> request) {
    String id = SecurityContextHolder.getContext().getAuthentication().getName();
    String newPassword = (String) request.get("newPassword");
    employeeService.resetPassword(id, newPassword);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Password reset successfully")
            .build());
  }

  @PutMapping("/{id}/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPasswordForUserByAdmin(
      @PathVariable String id, @RequestBody Map<String, Object> request) {
    String newPassword = (String) request.get("newPassword");
    employeeService.resetPassword(id, newPassword);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Password reset successfully")
            .build());
  }

  @PutMapping("/{id}/activate")
  public ResponseEntity<ApiResponse<Void>> activateUser(@PathVariable String id) {
    employeeService.activeEmployee(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User activated successfully")
            .build());
  }

  @PutMapping("/{id}/deactivate")
  public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable String id) {
    employeeService.deactivateEmployee(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User deactivated successfully")
            .build());
  }

  @Autowired private ApproveService approveService;

  @GetMapping("/{id}/approves")
  public ResponseEntity<ApiResponse<List<ApproveResponse>>> getApproves(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", approveService.getByApproverId(id)));
  }
}
