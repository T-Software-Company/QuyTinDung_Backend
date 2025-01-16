package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.OtherAssetDto;
import com.tsoftware.qtd.entity.OtherAsset;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.OtherAssetMapper;
import com.tsoftware.qtd.repository.OtherAssetRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OtherAssetService {

  private final OtherAssetRepository otherassetRepository;

  private final OtherAssetMapper otherassetMapper;

  public OtherAssetDto create(OtherAssetDto otherassetDto) {
    OtherAsset otherasset = otherassetMapper.toEntity(otherassetDto);
    return otherassetMapper.toDTO(otherassetRepository.save(otherasset));
  }

  public OtherAssetDto update(UUID id, OtherAssetDto otherassetDto) {
    OtherAsset otherasset =
        otherassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OtherAsset not found"));
    otherassetMapper.updateEntity(otherassetDto, otherasset);
    return otherassetMapper.toDTO(otherassetRepository.save(otherasset));
  }

  public void delete(UUID id) {
    otherassetRepository.deleteById(id);
  }

  public OtherAssetDto getById(UUID id) {
    OtherAsset otherasset =
        otherassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OtherAsset not found"));
    return otherassetMapper.toDTO(otherasset);
  }

  public List<OtherAssetDto> getAll() {
    return otherassetRepository.findAll().stream()
        .map(otherassetMapper::toDTO)
        .collect(Collectors.toList());
  }
}
