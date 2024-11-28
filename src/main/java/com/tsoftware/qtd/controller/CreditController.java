package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.dto.credit.*;
import com.tsoftware.qtd.service.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credits")
public class CreditController {

  private final CreditService creditService;
  private final AssetService assetService;
  private final LoanPlanService loanplanService;
  private final ValuationMeetingService valuationMeetingService;
  private final LoanRequestService loanRequestService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreditResponse>> create(@RequestParam Long customerId)
      throws Exception {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Created", creditService.create(customerId)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditResponse>> update(
      @PathVariable Long id, @RequestBody CreditRequest creditRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Updated", creditService.update(id, creditRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    creditService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched", creditService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CreditResponse>>> getAll() {
    return ResponseEntity.ok(
        new ApiResponse<>(HttpStatus.OK.value(), "Fetched All", creditService.getAll()));
  }

  @PostMapping("/{id}/loan-plan")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> create(
      @RequestBody LoanPlanRequest loanPlanRequest, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", loanplanService.create(loanPlanRequest, id)));
  }

  @PostMapping("/{id}/loan-request")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> create(
      @RequestBody LoanRequestRequest loanRequestRequest, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", loanRequestService.create(loanRequestRequest, id)));
  }

  @GetMapping("/{id}/assets")
  public ResponseEntity<ApiResponse<List<AssetResponse>>> getAssets(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", assetService.getAssetsByCreditId(id)));
  }

  @PostMapping("/{id}/valuation-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> create(
      @RequestBody ValuationMeetingRequest valuationMeetingRequest, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(),
            "Created",
            valuationMeetingService.create(valuationMeetingRequest, id)));
  }

  @GetMapping("/{id}/valuation-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> getByCreditId(
      @RequestBody ValuationMeetingRequest valuationMeetingRequest, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Created", valuationMeetingService.getByCreditId(id)));
  }
}
