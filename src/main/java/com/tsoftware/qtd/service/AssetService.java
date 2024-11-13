package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.AssetDto;
import java.util.List;

public interface AssetService {
  AssetDto create(AssetDto assetDto);

  AssetDto update(Long id, AssetDto assetDto);

  void delete(Long id);

  AssetDto getById(Long id);

  List<AssetDto> getAll();

  List<AssetDto> getAssetsByCreditId(Long id);
}
