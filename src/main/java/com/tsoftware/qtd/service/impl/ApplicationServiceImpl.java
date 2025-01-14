package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.application.*;
import com.tsoftware.qtd.entity.*;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApplicationMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.util.RequestUtil;
import java.util.ArrayList;
import java.util.List;
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
  private final EmployeeRepository employeeRepository;

  @Override
  @Transactional
  public ApplicationResponse create(UUID customerId) {
    var customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, customerId));
    List<Employee> loanProcessors = new ArrayList<>();
    employeeRepository.findByUserId(RequestUtil.getUserId()).ifPresent(loanProcessors::add);
    var application =
        Application.builder().loanProcessors(loanProcessors).customer(customer).build();
    return applicationMapper.toResponse(applicationRepository.save(application));
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
}
