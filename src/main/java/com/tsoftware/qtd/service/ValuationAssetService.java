package com.tsoftware.qtd.service;

import com.tsoftware.qtd.mapper.ValuationAssetMapper;
import com.tsoftware.qtd.repository.ValuationAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValuationAssetService {

  private final ValuationAssetRepository valuationassetRepository;

  private final ValuationAssetMapper valuationassetMapper;
}
