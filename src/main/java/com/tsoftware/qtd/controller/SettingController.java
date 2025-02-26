package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.setting.ApprovalSettingRequest;
import com.tsoftware.qtd.dto.setting.InterestRateSettingRequest;
import com.tsoftware.qtd.dto.setting.RatingCriterionSettingRequest;
import com.tsoftware.qtd.entity.ApprovalSetting;
import com.tsoftware.qtd.entity.InterestRateSetting;
import com.tsoftware.qtd.entity.RatingCriterionSetting;
import com.tsoftware.qtd.service.ApprovalSettingService;
import com.tsoftware.qtd.service.InterestRateSettingService;
import com.tsoftware.qtd.service.RatingCriterionSettingService;
import com.tsoftware.qtd.validation.IsUUID;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
@PreAuthorize("hasRole('ADMIN')")
public class SettingController {
  private final ApprovalSettingService approvalSettingService;
  private final InterestRateSettingService interestRateSettingService;
  private final RatingCriterionSettingService ratingCriterionSettingService;

  @GetMapping("/approval-settings")
  public ResponseEntity<?> getAllApprovalSettings(
      @Filter Specification<ApprovalSetting> spec, Pageable pageable) {
    return ResponseEntity.ok(approvalSettingService.getAll(spec, pageable));
  }

  @PostMapping("/approval-settings")
  public ResponseEntity<?> createApproveSetting(
      @RequestBody @Valid ApprovalSettingRequest approvalSettingRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Created",
                approvalSettingService.create(approvalSettingRequest)));
  }

  @PutMapping("/approval-settings/{id}")
  public ResponseEntity<?> updateApproveSetting(
      @RequestBody @Valid ApprovalSettingRequest approvalSettingRequest,
      @Valid @IsUUID @PathVariable String id) {
    return ResponseEntity.ok(
        approvalSettingService.update(approvalSettingRequest, UUID.fromString(id)));
  }

  @DeleteMapping("/approval-settings/{id}")
  public ResponseEntity<?> deleteApproveSetting(@Valid @IsUUID @PathVariable String id) {
    approvalSettingService.delete(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("interest-rate-settings")
  public ResponseEntity<?> getInterestRateSettings(
      @Filter Specification<InterestRateSetting> spec, Pageable pageable) {
    return ResponseEntity.ok(interestRateSettingService.findAll(spec, pageable));
  }

  @PostMapping("interest-rate-settings")
  public ResponseEntity<?> addInterestRateSetting(
      @Valid @RequestBody InterestRateSettingRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(), "Created", interestRateSettingService.create(request)));
  }

  @PutMapping("interest-rate-settings/{id}")
  public ResponseEntity<?> updateInterestRateSetting(
      @Valid @RequestBody InterestRateSettingRequest request,
      @Valid @IsUUID @PathVariable String id) {
    return ResponseEntity.ok(interestRateSettingService.update(request, UUID.fromString(id)));
  }

  @DeleteMapping("interest-rate-settings/{id}")
  public ResponseEntity<?> deleteInterestRateSetting(@Valid @IsUUID @PathVariable String id) {
    interestRateSettingService.delete(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }

  @GetMapping("rating-criterion-settings")
  public ResponseEntity<?> getRatingSettings(
      @Filter Specification<RatingCriterionSetting> spec, Pageable pageable) {
    return ResponseEntity.ok(ratingCriterionSettingService.findAll(spec, pageable));
  }

  @PostMapping("rating-criterion-settings")
  public ResponseEntity<?> createRatingCriterionSetting(
      @Valid @RequestBody RatingCriterionSettingRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Created",
                ratingCriterionSettingService.create(request)));
  }

  @PutMapping("rating-criterion-settings/{id}")
  public ResponseEntity<?> updateRatingCriterionSetting(
      @Valid @RequestBody RatingCriterionSettingRequest request,
      @Valid @IsUUID @PathVariable String id) {
    return ResponseEntity.ok(ratingCriterionSettingService.update(request, UUID.fromString(id)));
  }

  @DeleteMapping("rating-criterion-settings/{id}")
  public ResponseEntity<?> deleteRatingCriterionSetting(@Valid @IsUUID @PathVariable String id) {
    ratingCriterionSettingService.delete(UUID.fromString(id));
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Deleted", null));
  }
}
