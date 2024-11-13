package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.OtherAssetDto;
import java.util.List;

public interface OtherAssetService {
  OtherAssetDto create(OtherAssetDto otherassetDto);

  OtherAssetDto update(Long id, OtherAssetDto otherassetDto);

  void delete(Long id);

  OtherAssetDto getById(Long id);

  List<OtherAssetDto> getAll();
}
