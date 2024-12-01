package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.AssetRequest;
import com.tsoftware.qtd.dto.asset.AssetResponse;
import java.util.List;
import java.util.UUID;

public interface AssetService {
  AssetResponse create(AssetRequest assetRequest, UUID creditId);

  AssetResponse update(UUID id, AssetRequest assetRequest);

  void delete(UUID id);

  AssetResponse getById(UUID id);

  List<AssetResponse> getAll();

  List<AssetResponse> getAssetsByCreditId(UUID id);
}
