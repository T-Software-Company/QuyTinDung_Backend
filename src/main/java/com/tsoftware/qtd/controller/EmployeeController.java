package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.EmployeeUpdateRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.kcTransactionManager.KcTransactional;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.service.ApproveService;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.GroupService;
import com.tsoftware.qtd.service.KeycloakService;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  private final KeycloakService keycloakService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @KcTransactional(KcTransactional.KcTransactionType.CREATE_USER)
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
  public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee(
      @PathVariable @Valid @IsUUID String id) {
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .result(employeeService.getEmployee(UUID.fromString(id)))
            .build());
  }

  @PutMapping("/profile")
  @KcTransactional(KcTransactional.KcTransactionType.UPDATE_USER)
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
  @KcTransactional(KcTransactional.KcTransactionType.UPDATE_USER)
  public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
      @PathVariable @Valid @IsUUID String id, @RequestBody @Valid EmployeeUpdateRequest request) {

    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Profile updated successfully")
            .result(employeeService.updateEmployee(UUID.fromString(id), request))
            .build());
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<EmployeeResponse>> deleteEmployee(
      @PathVariable @Valid @IsUUID String id) {
    employeeService.delete(UUID.fromString(id));
    return ResponseEntity.ok(
        ApiResponse.<EmployeeResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Deleted")
            .result(null)
            .build());
  }

  @DeleteMapping
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
  public ResponseEntity<ApiResponse<Void>> employeeResetPassword() {
    String id = SecurityContextHolder.getContext().getAuthentication().getName();
    keycloakService.resetPasswordByEmail(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Password reset successfully")
            .build());
  }

  @PostMapping("/{id}/reset-password")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> resetPasswordForUserByAdmin(
      @PathVariable @Valid @IsUUID String id) {
    employeeService.resetPassword(UUID.fromString(id));
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Password reset successfully")
            .build());
  }

  @PostMapping("/{id}/enable")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> enable(@PathVariable @Valid @IsUUID String id) {
    employeeService.enable(UUID.fromString(id));
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User enabled successfully")
            .build());
  }

  @PostMapping("/{id}/disable")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> disable(@PathVariable @Valid @IsUUID String id) {
    employeeService.disable(UUID.fromString(id));
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Users disabled successfully")
            .build());
  }

  @PostMapping("/enables")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> enables(@RequestBody @Valid List<@IsUUID String> ids) {
    employeeService.enables(ids.stream().map(UUID::fromString).collect(Collectors.toList()));
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("Users enabled successfully")
            .build());
  }

  @PostMapping("/disables")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> disables(@RequestBody @Valid List<@IsUUID String> ids) {
    employeeService.disables(ids.stream().map(UUID::fromString).collect(Collectors.toList()));
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("User disabled successfully")
            .build());
  }

  @GetMapping("/{id}/approves")
  public ResponseEntity<ApiResponse<List<ApproveResponse>>> getApproves(
      @PathVariable @Valid @IsUUID String id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(),
            "Fetched All",
            approveService.getByApproverId(UUID.fromString(id))));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/join-group")
  @KcTransactional(KcTransactional.KcTransactionType.ADD_USER_TO_GROUP)
  public ResponseEntity<ApiResponse<Void>> joinGroup(
      @PathVariable @Valid @IsUUID String id, @RequestParam @Valid @IsUUID String groupId) {
    groupService.join(UUID.fromString(id), UUID.fromString(groupId));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Joined", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/add-roles")
  @KcTransactional(KcTransactional.KcTransactionType.ADD_ROLE_TO_USER)
  public ResponseEntity<ApiResponse<Void>> addRoles(
      @PathVariable @Valid @IsUUID String id,
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles) {
    employeeService.addRoles(UUID.fromString(id), roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added roles", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/remove-roles")
  @KcTransactional(KcTransactional.KcTransactionType.REMOVE_ROLE_ON_USER)
  public ResponseEntity<ApiResponse<Void>> removeRoles(
      @PathVariable @Valid @IsUUID String id,
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles) {
    employeeService.removeRoles(UUID.fromString(id), roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added roles", null));
  }
}
