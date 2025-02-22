package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.event.FinancialInfoSubmittedEvent;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.FinancialInfoMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.FinancialInfoRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FinancialInfoService {
  private final FinancialInfoRepository financialInfoRepository;
  private final FinancialInfoMapper financialInfoMapper;
  private final ApprovalProcessService approvalProcessService;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final ApplicationRepository applicationRepository;

  public ApprovalProcessResponse request(FinancialInfoRequest request) {
    var result =
        approvalProcessService.create(
            request, request.getApplication(), ProcessType.CREATE_FINANCIAL_INFO);
    applicationEventPublisher.publishEvent(new FinancialInfoSubmittedEvent(this, result));
    return result;
  }

  public FinancialInfoResponse create(FinancialInfoRequest request) {
    var application =
        applicationRepository
            .findById(UUID.fromString(request.getApplication().getId()))
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Application not found: " + request.getApplication().getId()));
    var entity = financialInfoMapper.toEntity(request);
    entity.setApplication(application);
    return financialInfoMapper.toResponse(financialInfoRepository.save(entity));
  }
}
