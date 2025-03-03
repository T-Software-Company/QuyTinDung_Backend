package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.entity.Asset;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AssetMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.AssetRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssetService {

  private final AssetRepository assetRepository;

  private final AssetMapper assetMapper;
  private final ApplicationRepository applicationRepository;
  private final ApprovalProcessService approvalProcessService;
  private final LoanRequestRepository loanRequestRepository;
  private final PageResponseMapper pageResponseMapper;

  public ApprovalProcessResponse request(List<AssetRequest> assetsRequest) {
    this.validateRequest(assetsRequest);
    return approvalProcessService.create(
        assetsRequest, assetsRequest.getFirst().getApplication(), ProcessType.CREATE_ASSETS);
  }

  private void validateRequest(List<AssetRequest> assetsRequest) {
    var applicationId = assetsRequest.getFirst().getApplication().getId();
    var loanRequest =
        loanRequestRepository
            .findByApplicationId(UUID.fromString(applicationId))
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "byApplicationId: " + applicationId));
    var assetsType = loanRequest.getLoanCollateralTypes();
    if (assetsType.size() != assetsRequest.size()) {
      throw new CommonException(
          ErrorType.CHECKSUM_INVALID, "Assets type are not matched with loan request");
    }
    assetsRequest.forEach(
        a -> {
          if (!loanRequest.getLoanCollateralTypes().contains(AssetType.valueOf(a.getAssetType()))) {
            throw new CommonException(
                ErrorType.CHECKSUM_INVALID,
                "Assets type are not matched with loan request, "
                    + a.getAssetType()
                    + "not found on loan request");
          }
        });
  }

  public List<AssetResponse> create(List<AssetRequest> assetsRequest, UUID applicationId) {
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "ApplicationId: " + applicationId));
    var entities = assetsRequest.stream().map(assetMapper::toEntity).toList();
    entities.forEach(e -> e.setApplication(application));
    var saved = assetRepository.saveAll(entities);
    return saved.stream().map(assetMapper::toResponse).toList();
  }

  public AssetResponse getById(UUID id) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new NotFoundException("Asset not found"));
    return assetMapper.toResponse(asset);
  }

  public PageResponse<AssetResponse> getAll(Specification<Asset> spec, Pageable page) {
    var result = assetRepository.findAll(spec, page).map(assetMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

  public ApprovalProcessResponse updateRequest(
      UUID approvalProcessId, List<AssetRequest> assetsRequest) {
    this.validateRequest(assetsRequest);
    return approvalProcessService.update(approvalProcessId, assetsRequest);
  }
}
