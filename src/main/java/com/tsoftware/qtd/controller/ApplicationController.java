package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.annotation.WorkflowAPI;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.AssetService;
import com.tsoftware.qtd.service.LoanPlanService;
import com.tsoftware.qtd.service.LoanRequestService;
import com.tsoftware.qtd.service.ValuationMeetingService;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.List;
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
  private final AssetService assetService;
  private final LoanPlanService loanplanService;
  private final ValuationMeetingService valuationMeetingService;
  private final LoanRequestService loanRequestService;

  @WorkflowAPI(step = "init")
  @PostMapping
  public ResponseEntity<?> create(@RequestParam @Valid @IsUUID String customerId) throws Exception {
    return ResponseEntity.ok(applicationService.create(UUID.fromString(customerId)));
  }

  // generate
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    applicationService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApplicationDTO> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(applicationService.getById(id));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<ApplicationResponse>>> getAll(
      @Filter Specification<Application> spec, Pageable page) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", applicationService.getAll(spec, page)));
  }

  @PostMapping("/{id}/loan-plan")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> create(
      @RequestBody LoanPlanDTO loanPlanDTO, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", loanplanService.create(loanPlanDTO, id)));
  }

  @PostMapping("/{id}/loan-request")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> create(
      @RequestBody LoanRequestDTO loanRequestDTO, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", loanRequestService.create(loanRequestDTO, id)));
  }

  @GetMapping("/{id}/assets")
  public ResponseEntity<ApiResponse<List<AssetResponse>>> getAssets(@PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", assetService.getAssetsByCreditId(id)));
  }

  @PostMapping("/{id}/valuation-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> create(
      @RequestBody ValuationMeetingRequest valuationMeetingRequest, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(),
            "Created",
            valuationMeetingService.create(valuationMeetingRequest, id)));
  }

  @GetMapping("/{id}/valuation-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> getByCreditId(
      @RequestBody ValuationMeetingRequest valuationMeetingRequest, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", valuationMeetingService.getByCreditId(id)));
  }
}
