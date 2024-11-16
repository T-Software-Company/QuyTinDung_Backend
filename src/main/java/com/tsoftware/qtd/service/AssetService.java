package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import java.util.List;

public interface AssetService {
  AssetResponse create(AssetRequest assetRequest, Long creditId);

  AssetResponse update(Long id, AssetRequest assetRequest);

  void delete(Long id);

  AssetResponse getById(Long id);

  List<AssetResponse> getAll();

  List<AssetResponse> getAssetsByCreditId(Long id);
}
