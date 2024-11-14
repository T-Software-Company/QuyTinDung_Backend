package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import com.tsoftware.qtd.entity.Credit;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CreditMapper;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import com.tsoftware.qtd.service.CreditService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditServiceImpl implements CreditService {

  @Autowired private CreditRepository creditRepository;

  @Autowired private CreditMapper creditMapper;

  @Autowired private DocumentService documentService;

  @Autowired private LoanPlanRepository loanPlanRepository;
  @Autowired private CustomerRepository customerRepository;

  @Override
  @Transactional
  public CreditResponse create(CreditRequest creditRequest, Long customerId) throws Exception {
    Credit credit = creditMapper.toEntity(creditRequest);
    var customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    credit.setCustomer(customer);
    var creditSaved = creditRepository.save(credit);
    var loanPlan = creditSaved.getLoanPlan();
    loanPlan.setCustomer(customer);
    var url =
        documentService.uploadDocument(loanPlan, "", "loan-plan" + UUID.randomUUID() + ".doc", 3);
    loanPlan.setDocumentUrl(url);

    var loanPlanSaved = loanPlanRepository.save(loanPlan);
    creditSaved.setLoanPlan(loanPlanSaved);
    return creditMapper.toResponse(creditSaved);
  }

  @Override
  public CreditResponse update(Long id, CreditRequest creditRequest) {
    Credit credit =
        creditRepository.findById(id).orElseThrow(() -> new NotFoundException("Credit not found"));
    creditMapper.updateEntity(creditRequest, credit);
    return creditMapper.toResponse(creditRepository.save(credit));
  }

  @Override
  public void delete(Long id) {
    creditRepository.deleteById(id);
  }

  @Override
  public CreditResponse getById(Long id) {
    Credit credit =
        creditRepository.findById(id).orElseThrow(() -> new NotFoundException("Credit not found"));
    return creditMapper.toResponse(credit);
  }

  @Override
  public List<CreditResponse> getAll() {
    return creditRepository.findAll().stream()
        .map(creditMapper::toResponse)
        .collect(Collectors.toList());
  }
}
