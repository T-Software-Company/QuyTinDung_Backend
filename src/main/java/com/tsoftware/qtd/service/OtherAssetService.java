package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.OtherAssetDto;
import java.util.List;
import java.util.UUID;

public interface OtherAssetService {
  OtherAssetDto create(OtherAssetDto otherassetDto);

  OtherAssetDto update(UUID id, OtherAssetDto otherassetDto);

  void delete(UUID id);

  OtherAssetDto getById(UUID id);

  List<OtherAssetDto> getAll();
}
