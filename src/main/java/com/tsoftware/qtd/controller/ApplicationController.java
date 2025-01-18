package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

  @WorkflowAPI(step = "init")
  @PostMapping
  public ResponseEntity<?> create(@RequestParam @Valid @IsUUID String customerId) throws Exception {
    return ResponseEntity.ok(applicationService.create(UUID.fromString(customerId)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(applicationService.getById(id));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<ApplicationResponse>>> getAll(
      @Filter Specification<Application> spec, Pageable page) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", applicationService.getAll(spec, page)));
  }
}
