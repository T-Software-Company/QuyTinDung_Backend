package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LandAssetDto;
import com.tsoftware.qtd.entity.LandAsset;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LandAssetMapper;
import com.tsoftware.qtd.repository.LandAssetRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LandAssetService {

  private final LandAssetRepository landassetRepository;

  private final LandAssetMapper landassetMapper;

  public LandAssetDto create(LandAssetDto landassetDto) {
    LandAsset landasset = landassetMapper.toEntity(landassetDto);
    return landassetMapper.toDTO(landassetRepository.save(landasset));
  }

  public LandAssetDto update(UUID id, LandAssetDto landassetDto) {
    LandAsset landasset =
        landassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAsset not found"));
    landassetMapper.updateEntity(landassetDto, landasset);
    return landassetMapper.toDTO(landassetRepository.save(landasset));
  }

  public void delete(UUID id) {
    landassetRepository.deleteById(id);
  }

  public LandAssetDto getById(UUID id) {
    LandAsset landasset =
        landassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAsset not found"));
    return landassetMapper.toDTO(landasset);
  }

  public List<LandAssetDto> getAll() {
    return landassetRepository.findAll().stream()
        .map(landassetMapper::toDTO)
        .collect(Collectors.toList());
  }
}
