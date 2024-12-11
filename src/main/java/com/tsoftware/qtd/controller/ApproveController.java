package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.service.ApproveService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approves")
public class ApproveController {

  @Autowired private ApproveService approveService;

  @PostMapping
  public ResponseEntity<ApiResponse<ApproveResponse>> create(
      @RequestBody ApproveResponse approveResponse) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", approveService.create(approveResponse)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ApproveResponse>> update(
      @PathVariable UUID id, @RequestBody ApproveResponse approveResponse) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", approveService.update(id, approveResponse)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    approveService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ApproveResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", approveService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ApproveResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", approveService.getAll()));
  }
}
