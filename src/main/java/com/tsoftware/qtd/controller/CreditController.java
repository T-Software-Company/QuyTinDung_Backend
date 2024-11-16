package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.dto.credit.*;
import com.tsoftware.qtd.service.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credits")
public class CreditController {

  @Autowired private CreditService creditService;
  @Autowired private AssetService assetService;
  @Autowired private LoanPlanService loanplanService;

  @Autowired private LoanRequestService loanRequestService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreditResponse>> create(@RequestParam Long customerId)
      throws Exception {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Created", creditService.create(customerId)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditResponse>> update(
      @PathVariable Long id, @RequestBody CreditRequest creditRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", creditService.update(id, creditRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    creditService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CreditResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", creditService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CreditResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", creditService.getAll()));
  }

  @PostMapping("/{id}/loan-plan")
  public ResponseEntity<ApiResponse<LoanPlanResponse>> create(
      @RequestBody LoanPlanRequest loanPlanRequest, @PathVariable Long creditId) throws Exception {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", loanplanService.create(loanPlanRequest, creditId)));
  }

  @PostMapping("/{id}/loan-request")
  public ResponseEntity<ApiResponse<LoanRequestResponse>> create(
      @RequestBody LoanRequestRequest loanRequestRequest, @PathVariable Long id) throws Exception {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", loanRequestService.create(loanRequestRequest, id)));
  }

  @GetMapping("/{id}/assets")
  public ResponseEntity<ApiResponse<List<AssetResponse>>> getAssets(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", assetService.getAssetsByCreditId(id)));
  }

  @Autowired private ValuationMeetingService valuationMeetingService;

  @PostMapping("/{id}/valuation-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> create(
      @RequestBody ValuationMeetingRequest valuationMeetingRequest, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Created", valuationMeetingService.create(valuationMeetingRequest, id)));
  }

  @GetMapping("/{id}/valuation-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingResponse>> getByCreditId(
      @RequestBody ValuationMeetingRequest valuationMeetingRequest, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", valuationMeetingService.getByCreditId(id)));
  }
}
