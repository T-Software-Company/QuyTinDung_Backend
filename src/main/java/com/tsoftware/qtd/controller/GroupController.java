package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.GroupDto;
import com.tsoftware.qtd.response.ApiResponse;
import com.tsoftware.qtd.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

  @Autowired private GroupService groupService;

  @PostMapping
  public ResponseEntity<ApiResponse<GroupDto>> create(@RequestBody GroupDto groupDto) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Created", groupService.create(groupDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<GroupDto>> update(
      @PathVariable Long id, @RequestBody GroupDto groupDto) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Updated", groupService.update(id, groupDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    groupService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GroupDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", groupService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<GroupDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", groupService.getAll()));
  }
}
