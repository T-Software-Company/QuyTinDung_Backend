package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.entity.OnboardingWorkflow;
import com.tsoftware.qtd.service.OnboardingWorkflowService;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onboarding-workflows")
@RequiredArgsConstructor
public class OnboardingWorkflowController {
  private final OnboardingWorkflowService workflowService;

  @GetMapping
  public ResponseEntity<?> getAll(
      @Filter Specification<OnboardingWorkflow> spec, Pageable pageable) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", workflowService.getAll(spec, pageable)));
  }

  @GetMapping("/{targetId}")
  public ResponseEntity<?> getById(@PathVariable @Valid @IsUUID String targetId) {
    return ResponseEntity.ok(workflowService.getByTargetId(targetId));
  }
}
