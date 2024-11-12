package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.AssetDto;
import com.tsoftware.qtd.entity.Asset;
import com.tsoftware.qtd.mapper.AssetMapper;
import com.tsoftware.qtd.repository.AssetRepository;
import com.tsoftware.qtd.service.AssetService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {

  @Autowired private AssetRepository assetRepository;

  @Autowired private AssetMapper assetMapper;

  @Override
  public AssetDto create(AssetDto assetDto) {
    Asset asset = assetMapper.toEntity(assetDto);
    return assetMapper.toDto(assetRepository.save(asset));
  }

  @Override
  public AssetDto update(Long id, AssetDto assetDto) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
    assetMapper.updateEntity(assetDto, asset);
    return assetMapper.toDto(assetRepository.save(asset));
  }

  @Override
  public void delete(Long id) {
    assetRepository.deleteById(id);
  }

  @Override
  public AssetDto getById(Long id) {
    Asset asset =
        assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
    return assetMapper.toDto(asset);
  }

  @Override
  public List<AssetDto> getAll() {
    return assetRepository.findAll().stream().map(assetMapper::toDto).collect(Collectors.toList());
  }
}
