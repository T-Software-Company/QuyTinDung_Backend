package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.OtherAssetRequest;
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

  public OtherAssetRequest create(OtherAssetRequest otherassetRequest) {
    OtherAsset otherasset = otherassetMapper.toEntity(otherassetRequest);
    return otherassetMapper.toDTO(otherassetRepository.save(otherasset));
  }

  public OtherAssetRequest update(UUID id, OtherAssetRequest otherassetRequest) {
    OtherAsset otherasset =
        otherassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OtherAsset not found"));
    otherassetMapper.updateEntity(otherassetRequest, otherasset);
    return otherassetMapper.toDTO(otherassetRepository.save(otherasset));
  }

  public void delete(UUID id) {
    otherassetRepository.deleteById(id);
  }

  public OtherAssetRequest getById(UUID id) {
    OtherAsset otherasset =
        otherassetRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OtherAsset not found"));
    return otherassetMapper.toDTO(otherasset);
  }

  public List<OtherAssetRequest> getAll() {
    return otherassetRepository.findAll().stream()
        .map(otherassetMapper::toDTO)
        .collect(Collectors.toList());
  }
}
