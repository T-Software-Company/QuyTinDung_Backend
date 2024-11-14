package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.LoanPlanRequest;
import com.tsoftware.qtd.dto.credit.LoanPlanResponse;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanPlanMapper;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import com.tsoftware.qtd.service.LoanPlanService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanPlanServiceImpl implements LoanPlanService {

  @Autowired private LoanPlanRepository loanplanRepository;

  @Autowired private LoanPlanMapper loanplanMapper;

  @Autowired private DocumentService documentService;
  @Autowired private CustomerRepository customerRepository;

  @Autowired private CreditRepository creditRepository;

  @Override
  public LoanPlanResponse create(LoanPlanRequest loanplanRequest, Long creditId) throws Exception {

    LoanPlan loanplan = loanplanMapper.toEntity(loanplanRequest);

    var credit =
        creditRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    loanplan.setCustomer(credit.getCustomer());
    loanplan.setCredit(credit);
    var url = documentService.uploadDocument(loanplan, "", "loan-plan" + UUID.randomUUID(), 3);
    loanplan.setDocumentUrl(url);
    return loanplanMapper.toDto(loanplanRepository.save(loanplan));
  }

  @Override
  public LoanPlanResponse update(Long id, LoanPlanRequest loanplanRequest) {
    LoanPlan loanplan =
        loanplanRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanPlan not found"));
    loanplanMapper.updateEntity(loanplanRequest, loanplan);
    return loanplanMapper.toDto(loanplanRepository.save(loanplan));
  }

  @Override
  public void delete(Long id) {
    loanplanRepository.deleteById(id);
  }

  @Override
  public LoanPlanResponse getById(Long id) {
    LoanPlan loanplan =
        loanplanRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanPlan not found"));
    return loanplanMapper.toDto(loanplan);
  }

  @Override
  public List<LoanPlanResponse> getAll() {
    return loanplanRepository.findAll().stream()
        .map(loanplanMapper::toDto)
        .collect(Collectors.toList());
  }
}
