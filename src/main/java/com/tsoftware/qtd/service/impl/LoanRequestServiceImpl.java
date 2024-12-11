package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.LoanRequestDTO;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import com.tsoftware.qtd.service.LoanRequestService;
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

  @Override
  public LoanRequestResponse create(LoanRequestDTO loanRequestDTO, UUID creditId) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest = loanrequestMapper.toEntity(loanRequestDTO);
    Application application =
        applicationRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    loanrequest.setCustomer(application.getCustomer());
    loanrequest.setApplication(application);
    application.setAmount(loanrequest.getAmount());
    application.setLoanSecurityType(loanrequest.getLoanSecurityType());
    applicationRepository.save(application);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  @Override
  public LoanRequestResponse update(UUID id, LoanRequestDTO loanRequestDTO) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    loanrequestMapper.updateEntity(loanRequestDTO, loanrequest);
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
