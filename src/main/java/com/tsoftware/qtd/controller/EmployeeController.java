package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.service.ApproveService;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.GroupService;
import com.tsoftware.qtd.validation.IsEnum;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
  private final GroupService groupService;
  private final PageResponseMapper pageResponseMapper;

  @PostMapping
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

  @GetMapping()
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<PageResponse<EmployeeResponse>>> getAll(
      @Filter Specification<Employee> spec, Pageable page) {
    Page<EmployeeResponse> employeesPage = employeeService.getAll(spec, page);
    var pageResponse = pageResponseMapper.toPageResponse(employeesPage);

    return ResponseEntity.ok(
        ApiResponse.<PageResponse<EmployeeResponse>>builder()
            .result(pageResponse)
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

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee(@PathVariable UUID id) {
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .result(employeeService.getEmployee(id))
            .build());
  }

  @PutMapping("/profile")
  public ResponseEntity<ApiResponse<EmployeeResponse>> updateMyProfile(
      @RequestBody @Valid ProfileRequest request) {
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
      @PathVariable UUID id, @RequestBody @Valid EmployeeRequest request) {

    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Profile updated successfully")
            .result(employeeService.updateEmployee(id, request))
            .build());
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<EmployeeResponse>> deleteEmployee(@PathVariable UUID id) {
    employeeService.delete(id);
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Deleted")
            .result(null)
            .build());
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<EmployeeResponse>> deleteEmployees(
      @RequestBody List<UUID> ids) {
    employeeService.delete(ids);
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Deleted")
            .result(null)
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

  @PostMapping("/{id}/enable")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> enable(@PathVariable UUID id) {
    employeeService.enable(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User enabled successfully")
            .build());
  }

  @PostMapping("/{id}/disable")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> disable(@PathVariable UUID id) {
    employeeService.disable(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Users disabled successfully")
            .build());
  }

  @PostMapping("/enables")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> enables(@RequestBody List<UUID> ids) {
    employeeService.enables(ids);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Users enabled successfully")
            .build());
  }

  @PostMapping("/disables")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> disables(@RequestBody List<UUID> ids) {
    employeeService.disables(ids);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User disabled successfully")
            .build());
  }

  @GetMapping("/{id}/approves")
  public ResponseEntity<ApiResponse<List<ApproveResponse>>> getApproves(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", approveService.getByApproverId(id)));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/join-group")
  public ResponseEntity<ApiResponse<Void>> joinGroup(
      @PathVariable UUID id, @RequestParam UUID groupId) {
    groupService.join(groupId, id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Joined", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/add-roles")
  public ResponseEntity<ApiResponse<Void>> addRoles(
      @PathVariable UUID id,
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles) {
    employeeService.addRoles(id, roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added roles", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/remove-roles")
  public ResponseEntity<ApiResponse<Void>> removeRoles(
      @PathVariable UUID id,
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles) {
    employeeService.removeRoles(id, roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added roles", null));
  }
}
