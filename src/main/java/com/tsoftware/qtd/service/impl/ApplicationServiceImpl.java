package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.credit.ApplicationRequest;
import com.tsoftware.qtd.dto.credit.ApplicationResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.entity.TransactionEntity;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApplicationMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.repository.TransactionRepository;
import com.tsoftware.qtd.service.ApplicationService;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;
  private final CustomerRepository customerRepository;
  private final TransactionRepository transactionRepository;

  @Override
  @Transactional
  public ApplicationResponse create(ApplicationRequest applicationRequest) {
    var customer =
        customerRepository
            .findById(applicationRequest.getTargetId())
            .orElseThrow(() -> new NotFoundException("Customer not found"));

    TransactionEntity loanRequestTransaction =
        TransactionEntity.builder()
            .PIC("user1")
            .multipleApproval(false)
            .metadata(applicationRequest.getLoanRequestDTO())
            .status(TransactionStatus.CREATED)
            .type(TransactionType.CREATE_LOAN_REQUEST)
            .customerId(applicationRequest.getTargetId())
            .createdAt(ZonedDateTime.now())
            .build();
    //    transactionRepository.save(loanRequestTransaction);
    TransactionEntity loanPlanTransaction =
        TransactionEntity.builder()
            .PIC("user1")
            .multipleApproval(false)
            .metadata(applicationRequest.getLoanPlan())
            .status(TransactionStatus.CREATED)
            .type(TransactionType.CREATE_LOAN_PLAN)
            .customerId(applicationRequest.getTargetId())
            .createdAt(ZonedDateTime.now())
            .build();
    //    transactionRepository.save(loanPlanTransaction);

    Application application = Application.builder().status(LoanStatus.CREATING).build();
    application.setCustomer(customer);
    application.setTransactions(Arrays.asList(loanRequestTransaction, loanPlanTransaction));
    applicationRepository.save(application);
    return ApplicationResponse.builder()
        .applicationId(application.getId())
        .transactionIds(List.of(loanRequestTransaction.getId(), loanPlanTransaction.getId()))
        .build();
  }

  @Override
  public ApplicationResponse update(UUID id, ApplicationRequest applicationRequest) {
    Application application =
        applicationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
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
  public ApplicationResponse getById(UUID id) {
    Application application =
        applicationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    return applicationMapper.toResponse(application);
  }

  @Override
  public List<ApplicationResponse> getAll() {
    return applicationRepository.findAll().stream()
        .map(applicationMapper::toResponse)
        .collect(Collectors.toList());
  }
}
