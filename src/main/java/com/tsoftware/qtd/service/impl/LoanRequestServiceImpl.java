package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import com.tsoftware.qtd.entity.Credit;
import com.tsoftware.qtd.entity.LoanRequest;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import com.tsoftware.qtd.service.LoanRequestService;
import java.util.List;
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
  private final CreditRepository creditRepository;

  @Override
  public LoanRequestResponse create(LoanRequestRequest loanRequestRequest, Long creditId) {
    LoanRequest loanrequest = loanrequestMapper.toEntity(loanRequestRequest);
    Credit credit =
        creditRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    loanrequest.setCustomer(credit.getCustomer());
    loanrequest.setCredit(credit);
    credit.setAmount(loanrequest.getAmount());
    credit.setLoanSecurityType(loanrequest.getLoanSecurityType());
    creditRepository.save(credit);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  @Override
  public LoanRequestResponse update(Long id, LoanRequestRequest loanRequestRequest) {
    LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    loanrequestMapper.updateEntity(loanRequestRequest, loanrequest);
    var credit = loanrequest.getCredit();
    credit.setAmount(loanrequest.getAmount());
    credit.setLoanSecurityType(loanrequest.getLoanSecurityType());
    creditRepository.save(credit);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  @Override
  public void delete(Long id) {
    loanrequestRepository.deleteById(id);
  }

  @Override
  public LoanRequestResponse getById(Long id) {
    LoanRequest loanrequest =
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
