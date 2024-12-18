package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.annotation.WorkflowAPI;
import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.AssetService;
import com.tsoftware.qtd.service.LoanPlanService;
import com.tsoftware.qtd.service.LoanRequestService;
import com.tsoftware.qtd.service.ValuationMeetingService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

  private final ApplicationService applicationService;
  private final AssetService assetService;
  private final LoanPlanService loanplanService;
  private final ValuationMeetingService valuationMeetingService;
  private final LoanRequestService loanRequestService;

  @WorkflowAPI
  @PostMapping
  public ResponseEntity<?> create(@RequestBody ApplicationRequest applicationRequest)
      throws Exception {
    return ResponseEntity.ok(applicationService.create(applicationRequest));
  }

  @WorkflowAPI
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ApplicationResponse>> update(
      @PathVariable UUID id, @RequestBody ApplicationRequest applicationRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Updated", applicationService.update(id, applicationRequest)));
  }

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
  public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched All", applicationService.getAll()));
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

  @GetMapping("/{id}/valuat ion-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> getByCreditId(
      @RequestBody ValuationMeetingRequest valuationMeetingRequest, @PathVariable UUID id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", valuationMeetingService.getByCreditId(id)));
  }
}
