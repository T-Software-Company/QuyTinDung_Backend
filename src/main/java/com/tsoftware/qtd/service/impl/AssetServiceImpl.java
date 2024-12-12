package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.entity.Asset;
import com.tsoftware.qtd.entity.LegalDocument;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AssetMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.AssetRepository;
import com.tsoftware.qtd.repository.LegalDocumentRepository;
import com.tsoftware.qtd.service.AssetService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

  private final AssetRepository assetRepository;

  private final AssetMapper assetMapper;
  private final ApplicationRepository applicationRepository;
  private final LegalDocumentRepository legalDocumentRepository;

  @Override
  public AssetResponse create(AssetRequest assetRequest, UUID creditId) {
    Asset asset = assetMapper.toEntity(assetRequest);
    List<LegalDocument> legalDocuments = asset.getLegalDocuments();
    var credit =
        applicationRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    asset.setApplication(credit);
    asset.setCustomer(credit.getCustomer());
    var assetSaved = assetRepository.save(asset);
    legalDocuments.forEach(
        l -> {
          l.setAsset(assetSaved);
          legalDocumentRepository.save(l);
        });

    return assetMapper.toResponse(assetSaved);
  }

  @Override
  public AssetResponse update(UUID id, AssetRequest assetRequest) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new NotFoundException("Asset not found"));
    assetMapper.updateEntity(assetRequest, asset);
    return assetMapper.toResponse(assetRepository.save(asset));
  }

  @Override
  public void delete(UUID id) {
    assetRepository.deleteById(id);
  }

  @Override
  public AssetResponse getById(UUID id) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new NotFoundException("Asset not found"));
    return assetMapper.toResponse(asset);
  }

  @Override
  public List<AssetResponse> getAll() {
    return assetRepository.findAll().stream()
        .map(assetMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<AssetResponse> getAssetsByCreditId(UUID id) {
    return assetRepository.findByApplicationId(id).stream()
        .map(assetMapper::toResponse)
        .collect(Collectors.toList());
  }
}
