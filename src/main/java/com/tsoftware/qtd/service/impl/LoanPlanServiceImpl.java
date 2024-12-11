package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.LoanPlanRequest;
import com.tsoftware.qtd.dto.credit.LoanPlanResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanPlanMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import com.tsoftware.qtd.service.LoanPlanService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanPlanServiceImpl implements LoanPlanService {

  private final LoanPlanRepository loanplanRepository;
  private final LoanPlanMapper loanplanMapper;
  private final ApplicationRepository applicationRepository;

  @Override
  public LoanPlanResponse create(LoanPlanRequest loanplanRequest, UUID applicationId) {

    LoanPlan loanplan = loanplanMapper.toEntity(loanplanRequest);
    Application application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new NotFoundException("Application not found"));
    loanplan.setCustomer(application.getCustomer());
    loanplan.setApplication(application);

    return loanplanMapper.toDto(loanplanRepository.save(loanplan));
  }

  @Override
  public LoanPlanResponse update(UUID id, LoanPlanRequest loanplanRequest) {
    LoanPlan loanplan =
        loanplanRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanPlan not found"));
    loanplanMapper.updateEntity(loanplanRequest, loanplan);
    return loanplanMapper.toDto(loanplanRepository.save(loanplan));
  }

  @Override
  public void delete(UUID id) {
    loanplanRepository.deleteById(id);
  }

  @Override
  public LoanPlanResponse getById(UUID id) {
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
