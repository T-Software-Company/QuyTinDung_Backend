package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;
import com.tsoftware.qtd.mapper.FinancialInfoMapper;
import com.tsoftware.qtd.repository.FinancialInfoRepository;
import com.tsoftware.qtd.service.FinancialInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FinancialInfoServiceImpl implements FinancialInfoService {
  private final FinancialInfoRepository financialInfoRepository;
  private final FinancialInfoMapper financialInfoMapper;

  public FinancialInfoResponse create(FinancialInfoRequest request) {
    var entity = financialInfoMapper.toEntity(request);
    return financialInfoMapper.toResponse(financialInfoRepository.save(entity));
  }
}
