package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.event.LoanRequestSubmittedEvent;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanRequestService {

  private final LoanRequestRepository loanrequestRepository;
  private final LoanRequestMapper loanrequestMapper;
  private final ApplicationRepository applicationRepository;
  private final ApprovalProcessService approvalProcessService;
  private final ApplicationContext applicationEventPublisher;

  public ApprovalProcessResponse request(LoanRequestRequest loanRequestRequest) {
    var result =
        approvalProcessService.create(
            loanRequestRequest,
            loanRequestRequest.getApplication(),
            ProcessType.CREATE_LOAN_REQUEST);
    applicationEventPublisher.publishEvent(new LoanRequestSubmittedEvent(this, result));
    return result;
  }

  public LoanRequestResponse create(LoanRequestRequest loanRequestRequest, UUID applicationId) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestMapper.toEntity(loanRequestRequest);
    Application application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    loanrequest.setApplication(application);
    application.setAmount(loanrequest.getAmount());
    application.setLoanSecurityType(loanrequest.getLoanSecurityType());
    applicationRepository.save(application);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  public LoanRequestResponse update(UUID id, LoanRequestRequest loanRequestRequest) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    loanrequestMapper.updateEntity(loanRequestRequest, loanrequest);
    var credit = loanrequest.getApplication();
    credit.setAmount(loanrequest.getAmount());
    credit.setLoanSecurityType(loanrequest.getLoanSecurityType());
    applicationRepository.save(credit);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  public void delete(UUID id) {
    loanrequestRepository.deleteById(id);
  }

  public LoanRequestResponse getById(UUID id) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    return loanrequestMapper.toResponse(loanrequest);
  }

  public List<LoanRequestResponse> getAll() {
    return loanrequestRepository.findAll().stream()
        .map(loanrequestMapper::toResponse)
        .collect(Collectors.toList());
  }
}
