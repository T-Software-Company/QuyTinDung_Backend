package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LandAssetDto;
import java.util.List;

public interface LandAssetService {
  LandAssetDto create(LandAssetDto landassetDto);

  LandAssetDto update(Long id, LandAssetDto landassetDto);

  void delete(Long id);

  LandAssetDto getById(Long id);

  List<LandAssetDto> getAll();
}
