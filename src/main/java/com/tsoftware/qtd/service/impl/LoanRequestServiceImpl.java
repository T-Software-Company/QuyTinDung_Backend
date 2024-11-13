package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import com.tsoftware.qtd.entity.LoanRequest;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import com.tsoftware.qtd.service.LoanRequestService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

  @Autowired private LoanRequestRepository loanrequestRepository;

  @Autowired private LoanRequestMapper loanrequestMapper;

  @Override
  public LoanRequestResponse create(LoanRequestRequest loanrequestRequest) {
    LoanRequest loanrequest = loanrequestMapper.toEntity(loanrequestRequest);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  @Override
  public LoanRequestResponse update(Long id, LoanRequestRequest loanrequestRequest) {
    LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    loanrequestMapper.updateEntity(loanrequestRequest, loanrequest);
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
