package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import com.tsoftware.qtd.entity.Asset;
import com.tsoftware.qtd.entity.LegalDocument;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AssetMapper;
import com.tsoftware.qtd.repository.AssetRepository;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.repository.LegalDocumentRepository;
import com.tsoftware.qtd.service.AssetService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {

  @Autowired private AssetRepository assetRepository;

  @Autowired private AssetMapper assetMapper;
  @Autowired private CreditRepository creditRepository;
  @Autowired private LegalDocumentRepository legalDocumentRepository;

  @Override
  public AssetResponse create(AssetRequest assetRequest, Long creditId) {
    Asset asset = assetMapper.toEntity(assetRequest);
    List<LegalDocument> legalDocuments = asset.getLegalDocuments();
    var credit =
        creditRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    asset.setCredit(credit);
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
  public AssetResponse update(Long id, AssetRequest assetRequest) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new NotFoundException("Asset not found"));
    assetMapper.updateEntity(assetRequest, asset);
    return assetMapper.toResponse(assetRepository.save(asset));
  }

  @Override
  public void delete(Long id) {
    assetRepository.deleteById(id);
  }

  @Override
  public AssetResponse getById(Long id) {
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
  public List<AssetResponse> getAssetsByCreditId(Long id) {
    return assetRepository.findByCreditId(id).stream()
        .map(assetMapper::toResponse)
        .collect(Collectors.toList());
  }
}
