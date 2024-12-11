package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LandAssetDto;
import java.util.List;
import java.util.UUID;

public interface LandAssetService {
  LandAssetDto create(LandAssetDto landassetDto);

  LandAssetDto update(UUID id, LandAssetDto landassetDto);

  void delete(UUID id);

  LandAssetDto getById(UUID id);

  List<LandAssetDto> getAll();
}
