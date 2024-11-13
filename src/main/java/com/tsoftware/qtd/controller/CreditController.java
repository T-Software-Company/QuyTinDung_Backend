package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingDto;
import com.tsoftware.qtd.dto.asset.AssetDto;
import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import com.tsoftware.qtd.service.AssetService;
import com.tsoftware.qtd.service.CreditService;
import com.tsoftware.qtd.service.ValuationMeetingService;
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

  @PostMapping
  public ResponseEntity<ApiResponse<CreditResponse>> create(
      @RequestBody CreditRequest creditRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", creditService.create(creditRequest)));
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

  @GetMapping("/{id}/assets")
  public ResponseEntity<ApiResponse<List<AssetDto>>> getAssets(@PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            HttpStatus.OK.value(), "Fetched All", assetService.getAssetsByCreditId(id)));
  }

  @Autowired private ValuationMeetingService valuationmeetingService;

  @PostMapping("/{id}/valuation-meeting")
  public ResponseEntity<ApiResponse<ValuationMeetingDto>> create(
      @RequestBody ValuationMeetingDto valuationmeetingDto, @PathVariable Long id) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            1000, "Created", valuationmeetingService.create(valuationmeetingDto, id)));
  }

  //  @PutMapping("/{id}/valuation-meeting")
  //  public ResponseEntity<ApiResponse<ValuationMeetingDto>> update(@PathVariable Long id,
  // @RequestBody ValuationMeetingDto valuationmeetingDto) {
  //    return ResponseEntity.ok(new ApiResponse<>(1000, "Updated",
  // valuationmeetingService.update(id, valuationmeetingDto)));
  //  }
  //
  //  @DeleteMapping("/{id}")
  //  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
  //    valuationmeetingService.delete(id);
  //    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  //  }
  //
  //  @GetMapping("/{id}")
  //  public ResponseEntity<ApiResponse<ValuationMeetingDto>> getById(@PathVariable Long id) {
  //    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched",
  // valuationmeetingService.getById(id)));
  //  }
  //
  //  @GetMapping
  //  public ResponseEntity<ApiResponse<List<ValuationMeetingDto>>> getAll() {
  //    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All",
  // valuationmeetingService.getAll()));
  //  }
}
