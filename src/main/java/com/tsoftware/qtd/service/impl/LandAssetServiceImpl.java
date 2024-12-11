package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.LandAssetDto;
import com.tsoftware.qtd.entity.LandAsset;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LandAssetMapper;
import com.tsoftware.qtd.repository.LandAssetRepository;
import com.tsoftware.qtd.service.LandAssetService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandAssetServiceImpl implements LandAssetService {

  @Autowired private LandAssetRepository landassetRepository;

  @Autowired private LandAssetMapper landassetMapper;

  @Override
  public LandAssetDto create(LandAssetDto landassetDto) {
    LandAsset landasset = landassetMapper.toEntity(landassetDto);
    return landassetMapper.toDto(landassetRepository.save(landasset));
  }

  @Override
  public LandAssetDto update(UUID id, LandAssetDto landassetDto) {
    LandAsset landasset =
        landassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAsset not found"));
    landassetMapper.updateEntity(landassetDto, landasset);
    return landassetMapper.toDto(landassetRepository.save(landasset));
  }

  @Override
  public void delete(UUID id) {
    landassetRepository.deleteById(id);
  }

  @Override
  public LandAssetDto getById(UUID id) {
    LandAsset landasset =
        landassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAsset not found"));
    return landassetMapper.toDto(landasset);
  }

  @Override
  public List<LandAssetDto> getAll() {
    return landassetRepository.findAll().stream()
        .map(landassetMapper::toDto)
        .collect(Collectors.toList());
  }
}
