package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanPlanMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanPlanService {

  private final LoanPlanRepository loanplanRepository;
  private final LoanPlanMapper loanplanMapper;
  private final ApplicationRepository applicationRepository;

  public LoanPlanResponse create(LoanPlanRequest loanplanRequest, UUID applicationId) {

    LoanPlan loanplan = loanplanMapper.toEntity(loanplanRequest);
    Application application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new NotFoundException("Application not found"));
    loanplan.setApplication(application);

    return loanplanMapper.toDTO(loanplanRepository.save(loanplan));
  }

  public LoanPlanResponse update(UUID id, LoanPlanRequest loanplanRequest) {
    LoanPlan loanplan =
        loanplanRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanPlan not found"));
    loanplanMapper.updateEntity(loanplanRequest, loanplan);
    return loanplanMapper.toDTO(loanplanRepository.save(loanplan));
  }

  public void delete(UUID id) {
    loanplanRepository.deleteById(id);
  }

  public LoanPlanResponse getById(UUID id) {
    LoanPlan loanplan =
        loanplanRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanPlan not found"));
    return loanplanMapper.toDTO(loanplan);
  }

  public List<LoanPlanResponse> getAll() {
    return loanplanRepository.findAll().stream()
        .map(loanplanMapper::toDTO)
        .collect(Collectors.toList());
  }
}
