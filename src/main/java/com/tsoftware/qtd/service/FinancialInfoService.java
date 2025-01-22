package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.mapper.FinancialInfoMapper;
import com.tsoftware.qtd.repository.FinancialInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FinancialInfoService {
  private final FinancialInfoRepository financialInfoRepository;
  private final FinancialInfoMapper financialInfoMapper;
  private final ApprovalProcessService approvalProcessService;

  public ApprovalProcessResponse request(FinancialInfoRequest request) {
    return approvalProcessService.create(
        request, request.getApplication(), ProcessType.CREATE_FINANCIAL_INFO);
  }

  public FinancialInfoResponse create(FinancialInfoRequest request) {
    var entity = financialInfoMapper.toEntity(request);
    return financialInfoMapper.toResponse(financialInfoRepository.save(entity));
  }
}
