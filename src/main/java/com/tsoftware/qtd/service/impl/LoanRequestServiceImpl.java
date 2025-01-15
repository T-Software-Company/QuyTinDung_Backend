package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApplicationMapper;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.mapper.WorkflowTransactionMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import com.tsoftware.qtd.repository.WorkflowTransactionRepository;
import com.tsoftware.qtd.service.LoanRequestService;
import com.tsoftware.qtd.util.RequestUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanRequestServiceImpl implements LoanRequestService {

  private final LoanRequestRepository loanrequestRepository;
  private final LoanRequestMapper loanrequestMapper;
  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;
  private final WorkflowTransactionMapper workflowTransactionMapper;
  private final WorkflowTransactionRepository workflowTransactionRepository;

  @Override
  public WorkflowTransactionResponse request(LoanRequestRequest loanRequestRequest) {
    var transaction =
        WorkflowTransactionDTO.builder()
            .application(applicationMapper.toDTO(loanRequestRequest.getApplication()))
            .PIC(RequestUtil.getUserId())
            .type(TransactionType.CREATE_LOAN_REQUEST)
            .approves(null)
            .metadata(loanRequestRequest)
            .build(); // wip

    var saved = workflowTransactionRepository.save(workflowTransactionMapper.toEntity(transaction));
    return workflowTransactionMapper.toResponse(saved);
  }

  @Override
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

  @Override
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

  @Override
  public void delete(UUID id) {
    loanrequestRepository.deleteById(id);
  }

  @Override
  public LoanRequestResponse getById(UUID id) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    return loanrequestMapper.toResponse(loanrequest);
  }

  @Override
  public List<LoanRequestResponse> getAll() {
    return loanrequestRepository.findAll().stream()
        .map(loanrequestMapper::toResponse)
        .collect(Collectors.toList());
  }
}
