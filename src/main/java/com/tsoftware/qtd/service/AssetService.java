package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.entity.Asset;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AssetMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.AssetRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssetService {

  private final AssetRepository assetRepository;

  private final AssetMapper assetMapper;
  private final ApplicationRepository applicationRepository;

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

  public AssetResponse update(UUID id, AssetRequest assetRequest) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new NotFoundException("Asset not found"));
    assetMapper.updateEntity(assetRequest, asset);
    return assetMapper.toResponse(assetRepository.save(asset));
  }

  public void delete(UUID id) {
    assetRepository.deleteById(id);
  }

  public AssetResponse getById(UUID id) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new NotFoundException("Asset not found"));
    return assetMapper.toResponse(asset);
  }

  public List<AssetResponse> getAll() {
    return assetRepository.findAll().stream()
        .map(assetMapper::toResponse)
        .collect(Collectors.toList());
  }

  public List<AssetResponse> getAssetsByCreditId(UUID id) {
    return assetRepository.findByApplicationId(id).stream()
        .map(assetMapper::toResponse)
        .collect(Collectors.toList());
  }
}
