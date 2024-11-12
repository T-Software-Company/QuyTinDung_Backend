package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.AssetRepissessionNoticeDto;
import java.util.List;

public interface AssetRepissessionNoticeService {
  AssetRepissessionNoticeDto create(AssetRepissessionNoticeDto assetrepissessionnoticeDto);

  AssetRepissessionNoticeDto update(Long id, AssetRepissessionNoticeDto assetrepissessionnoticeDto);

  void delete(Long id);

  AssetRepissessionNoticeDto getById(Long id);

  List<AssetRepissessionNoticeDto> getAll();
}
