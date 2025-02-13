package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.constants.WorkflowStep;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

  private final ApplicationService applicationService;

  @PostMapping
  @WorkflowAPI(step = WorkflowStep.INIT, action = WorkflowAPI.WorkflowAction.CREATE)
  public ResponseEntity<?> create(@RequestParam @Valid @IsUUID String customerId) throws Exception {
    return ResponseEntity.ok(applicationService.create(UUID.fromString(customerId)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable @Valid @IsUUID String id) {
    return ResponseEntity.ok(applicationService.getById(UUID.fromString(id)));
  }

  @GetMapping
  public ResponseEntity<?> getAll(@Filter Specification<Application> spec, Pageable page) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", applicationService.getAll(spec, page)));
  }

  @PostMapping("/{id}/cancel")
  @WorkflowAPI(action = WorkflowAPI.WorkflowAction.CANCEL)
  public ResponseEntity<?> cancel(
      @PathVariable(name = "id") @Valid @IsUUID @TargetId String applicationId) {
    applicationService.cancel(UUID.fromString(applicationId));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Cancelled", null));
  }
}
