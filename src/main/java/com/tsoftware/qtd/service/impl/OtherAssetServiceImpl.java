package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.OtherAssetDto;
import com.tsoftware.qtd.entity.OtherAsset;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.OtherAssetMapper;
import com.tsoftware.qtd.repository.OtherAssetRepository;
import com.tsoftware.qtd.service.OtherAssetService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtherAssetServiceImpl implements OtherAssetService {

  @Autowired private OtherAssetRepository otherassetRepository;

  @Autowired private OtherAssetMapper otherassetMapper;

  @Override
  public OtherAssetDto create(OtherAssetDto otherassetDto) {
    OtherAsset otherasset = otherassetMapper.toEntity(otherassetDto);
    return otherassetMapper.toDto(otherassetRepository.save(otherasset));
  }

  @Override
  public OtherAssetDto update(Long id, OtherAssetDto otherassetDto) {
    OtherAsset otherasset =
        otherassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OtherAsset not found"));
    otherassetMapper.updateEntity(otherassetDto, otherasset);
    return otherassetMapper.toDto(otherassetRepository.save(otherasset));
  }

  @Override
  public void delete(Long id) {
    otherassetRepository.deleteById(id);
  }

  @Override
  public OtherAssetDto getById(Long id) {
    OtherAsset otherasset =
        otherassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OtherAsset not found"));
    return otherassetMapper.toDto(otherasset);
  }

  @Override
  public List<OtherAssetDto> getAll() {
    return otherassetRepository.findAll().stream()
        .map(otherassetMapper::toDto)
        .collect(Collectors.toList());
  }
}
