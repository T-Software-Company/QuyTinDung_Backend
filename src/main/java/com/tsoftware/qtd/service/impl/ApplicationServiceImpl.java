package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
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
import java.util.ArrayList;
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
            .orElseThrow(() -> new NotFoundException("Customer not found."));

    TransactionEntity loanRequestTransaction =
        TransactionEntity.builder()
            .PIC("user1")
            .metadata(applicationRequest.getLoanRequest())
            .status(ApproveStatus.WAIT)
            .type(TransactionType.CREATE_LOAN_REQUEST)
            .customerId(applicationRequest.getTargetId())
            .approvers(new ArrayList<>())
            .requiredApprovals(1)
            .createdAt(ZonedDateTime.now())
            .metadata(applicationRequest.getLoanRequest())
            .build();
    //    transactionRepository.save(loanRequestTransaction);
    TransactionEntity loanPlanTransaction =
        TransactionEntity.builder()
            .PIC("user1")
            .metadata(applicationRequest.getLoanPlan())
            .status(ApproveStatus.WAIT)
            .type(TransactionType.CREATE_LOAN_PLAN)
            .customerId(applicationRequest.getTargetId())
            .approvers(new ArrayList<>())
            .requiredApprovals(2)
            .createdAt(ZonedDateTime.now())
            .metadata(applicationRequest.getLoanPlan())
            .build();
    //    transactionRepository.save(loanPlanTransaction);

    Application application = Application.builder().status(LoanStatus.CREATING).build();
    application.setCustomer(customer);

    loanRequestTransaction.setApplication(application);
    loanPlanTransaction.setApplication(application);

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
  public ApplicationDTO getById(UUID id) {
    Application application =
        applicationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Application not found"));
    return applicationMapper.toDTO(application);
  }

  @Override
  public List<ApplicationResponse> getAll() {
    return applicationRepository.findAll().stream()
        .map(applicationMapper::toResponse)
        .collect(Collectors.toList());
  }
}
