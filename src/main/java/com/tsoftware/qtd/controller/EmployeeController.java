package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.constants.EnumType.Role;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/employees")
public class EmployeeController {

  EmployeeService employeeService;
  ApproveService approveService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<EmployeeResponse>> create(
      @RequestBody @Valid EmployeeRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ApiResponse.<EmployeeResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("successfully")
                .result(employeeService.createEmployee(request))
                .build());
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
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
  public ResponseEntity<ApiResponse<EmployeeResponse>> updateMyProfile(
      @RequestBody @Valid ProfileRequest request) {
    ;
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Profile updated successfully")
            .result(employeeService.updateProfile(request))
            .build());
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
      @PathVariable String id, @RequestBody @Valid EmployeeRequest request) {

    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Profile updated successfully")
            .result(employeeService.updateEmployee(id, request))
            .build());
  }

  @GetMapping("/roles")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Role[]>> getRoles() {
    return ResponseEntity.ok(new ApiResponse<>(200, "Successfully", Role.values()));
  }

  @PostMapping("/reset-password")
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

  @PostMapping("/{id}/reset-password")
  @PreAuthorize("hasRole('ADMIN')")
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

  @PostMapping("/{id}/activate")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> activateUser(@PathVariable String id) {
    employeeService.activeEmployee(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User activated successfully")
            .build());
  }

  @PostMapping("/{id}/deactivate")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable String id) {
    employeeService.deactivateEmployee(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User deactivated successfully")
            .build());
  }

  @GetMapping("/{id}/approves")
  public ResponseEntity<ApiResponse<List<ApproveResponse>>> getApproves(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Fetched All", approveService.getByApproverId(id)));
  }
}
