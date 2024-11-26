package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.service.GroupService;
import com.tsoftware.qtd.validation.IsEnum;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GroupController {

  GroupService groupService;
  private final PageResponseMapper pageResponseMapper;

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ApiResponse<GroupResponse>> create(
      @RequestBody @Valid GroupRequest groupRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Created", groupService.create(groupRequest)));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<GroupResponse>> update(
      @PathVariable Long id, @RequestBody @Valid GroupRequest groupRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Updated", groupService.update(id, groupRequest)));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    groupService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GroupResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", groupService.getById(id)));
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
  public ResponseEntity<ApiResponse<Void>> joinGroup(
      @PathVariable Long id, @RequestParam Long employeeId) {
    groupService.join(id, employeeId);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/remove-employee")
  public ResponseEntity<ApiResponse<Void>> LeaveGroup(
      @PathVariable Long id, @RequestParam Long employeeId) {
    groupService.leave(id, employeeId);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Removed", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/add-roles")
  public ResponseEntity<ApiResponse<Void>> addRoles(
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles,
      @PathVariable Long id) {
    groupService.addRoles(id, roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Added", null));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/remove-roles")
  public ResponseEntity<ApiResponse<Void>> removeRoles(
      @RequestBody @Valid @IsEnum(enumClass = Role.class) List<String> roles,
      @PathVariable Long id) {
    groupService.removeRoles(id, roles);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Removed", null));
  }
}
