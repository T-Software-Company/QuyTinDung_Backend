package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.kcTransactionManager.KcTransactional;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.service.GroupService;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/groups")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GroupController {

  GroupService groupService;
  private final PageResponseMapper pageResponseMapper;

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping
  @KcTransactional(KcTransactional.KcTransactionType.CREATE_GROUP)
  public ResponseEntity<ApiResponse<GroupResponse>> create(
      @RequestBody @Valid GroupRequest groupRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Created", groupService.create(groupRequest)));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping("/{id}")
  @KcTransactional(KcTransactional.KcTransactionType.UPDATE_GROUP)
  public ResponseEntity<ApiResponse<GroupResponse>> update(
      @PathVariable @Valid @IsUUID String id, @RequestBody @Valid GroupRequest groupRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(),
            "Updated",
            groupService.update(UUID.fromString(id), groupRequest)));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/{id}")
  @KcTransactional(KcTransactional.KcTransactionType.DELETE_GROUP)
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @Valid @IsUUID String id) {
    groupService.delete(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GroupResponse>> getById(
      @PathVariable @Valid @IsUUID String id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched", groupService.getById(UUID.fromString(id))));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<ApiResponse<PageResponse<GroupResponse>>> getAll(
      @Filter Specification<Group> spec, Pageable page) {
    Page<GroupResponse> groups = groupService.getAll(spec, page);

    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", pageResponseMapper.toPageResponse(groups)));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/add-employee")
  @KcTransactional(KcTransactional.KcTransactionType.ADD_USER_TO_GROUP)
  public ResponseEntity<ApiResponse<Void>> joinGroup(
      @PathVariable @Valid @IsUUID String id, @RequestParam @Valid @IsUUID String employeeId) {
    groupService.join(UUID.fromString(id), UUID.fromString(employeeId));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/remove-employee")
  @KcTransactional(KcTransactional.KcTransactionType.REMOVE_USER_ON_GROUP)
  public ResponseEntity<ApiResponse<Void>> LeaveGroup(
      @PathVariable @Valid @IsUUID String id, @RequestParam @Valid @IsUUID String employeeId) {
    groupService.leave(UUID.fromString(id), UUID.fromString(employeeId));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Removed", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/add-roles")
  @KcTransactional(KcTransactional.KcTransactionType.ADD_ROLE_TO_GROUP)
  public ResponseEntity<ApiResponse<Void>> addRoles(
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles,
      @PathVariable @Valid @IsUUID String id) {
    groupService.addRoles(UUID.fromString(id), roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/remove-roles")
  @KcTransactional(KcTransactional.KcTransactionType.REMOVE_ROLE_ON_GROUP)
  public ResponseEntity<ApiResponse<Void>> removeRoles(
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles,
      @PathVariable @Valid @IsUUID String id) {
    groupService.removeRoles(UUID.fromString(id), roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Removed", null));
  }
}
