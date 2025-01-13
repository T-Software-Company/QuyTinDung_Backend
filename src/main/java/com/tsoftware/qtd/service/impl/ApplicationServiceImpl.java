package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.entity.FinancialInfo;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.entity.LoanRequest;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApplicationMapper;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.LoanService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;
  private final CustomerRepository customerRepository;
  private final DtoMapper mapper;
  private final EmployeeService employeeService;
  private final LoanService loanService;

  @Override
  @Transactional
  public ApplicationResponse create(UUID customerId) {
    return null;
  }

  @Override
  public ApplicationResponse update(UUID id, ApplicationDTO applicationRequest) {
    Application application =
        applicationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Application not found"));
    applicationMapper.updateEntity(applicationRequest, application);
    return applicationMapper.toResponse(applicationRepository.save(application));
  }

  @Override
  public void delete(UUID id) {
    Application application =
        applicationRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    application.setIsDeleted(true);
    applicationRepository.save(application);
  }

  @Override
  public ApplicationDTO getById(UUID id) {
    Application application =
        applicationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Application not found"));

    return applicationMapper.toDTO(application);
  }

  @Override
  public Page<ApplicationResponse> getAll(Specification<Application> spec, Pageable page) {
    return applicationRepository.findAll(spec, page).map(applicationMapper::toResponse);
  }

  @Override
  public void createOrUpdateFinancialInfo(UUID applicationId, FinancialInfoDTO financialInfoDTO) {
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, applicationId));
    var entity = Optional.ofNullable(application.getFinancialInfo()).orElseGet(FinancialInfo::new);
    mapper.updateEntity(entity, financialInfoDTO);
    entity.setApplication(application);
    application.setFinancialInfo(entity);
    applicationRepository.save(application);
  }

  @Override
  public void createOrUpdateLoanPlan(UUID applicationId, LoanPlanDTO loanPlanDTO) {
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, applicationId));
    var entity = Optional.ofNullable(application.getLoanPlan()).orElseGet(LoanPlan::new);
    mapper.updateEntity(entity, loanPlanDTO);
    entity.setApplication(application);
    application.setLoanPlan(entity);
    applicationRepository.save(application);
  }

  @Override
  public void createOrUpdateLoanRequest(UUID applicationId, LoanRequestDTO loanRequestDTO) {
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, applicationId));
    var entity = Optional.ofNullable(application.getLoanRequest()).orElseGet(LoanRequest::new);
    mapper.updateEntity(entity, loanRequestDTO);
    entity.setApplication(application);
    application.setLoanRequest(entity);
    applicationRepository.save(application);
  }
}
