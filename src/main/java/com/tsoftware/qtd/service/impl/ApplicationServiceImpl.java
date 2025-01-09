package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.dto.transaction.TransactionDTO;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.entity.Approve;
import com.tsoftware.qtd.entity.FinancialInfo;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.entity.LoanRequest;
import com.tsoftware.qtd.entity.TransactionEntity;
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
import com.tsoftware.qtd.util.RequestUtil;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
    var customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new NotFoundException("Customer not found."));

    var currentApplication = applicationRepository.findByCustomerId(customerId);
    if (currentApplication.stream()
        .filter(app -> !app.getIsDeleted())
        .anyMatch(app -> LoanStatus.CREATING.equals(app.getStatus()))) {
      throw new CommonException(ErrorType.HAS_APPLICATION_IN_PROGRESS);
    }

    Application application = Application.builder().status(LoanStatus.CREATING).build();
    application.setCustomer(customer);
    List<TransactionEntity> transactions = new ArrayList<>();

    if (Objects.nonNull(applicationRequest.getLoanRequest())) {
      TransactionEntity loanRequestTransaction =
          buildTransactionEntity(
              TransactionType.CREATE_LOAN_REQUEST,
              applicationRequest.getLoanRequest(),
              customerId,
              applicationRequest.getLoanRequest().getAssignees(),
              1);
      loanRequestTransaction.setApplication(application);
      transactions.add(loanRequestTransaction);
    }

    if (Objects.nonNull(applicationRequest.getLoanPlan())) {
      TransactionEntity loanPlanTransaction =
          buildTransactionEntity(
              TransactionType.CREATE_LOAN_PLAN,
              applicationRequest.getLoanPlan(),
              customerId,
              applicationRequest.getLoanRequest().getAssignees(),
              1);
      loanPlanTransaction.setApplication(application);
      transactions.add(loanPlanTransaction);
    }

    if (Objects.nonNull(applicationRequest.getFinancialInfo())) {
      TransactionEntity financialInfoTransaction =
          buildTransactionEntity(
              TransactionType.CREATE_FINANCIAL_INFO,
              applicationRequest.getFinancialInfo(),
              customerId,
              applicationRequest.getLoanRequest().getAssignees(),
              1);
      financialInfoTransaction.setApplication(application);
      transactions.add(financialInfoTransaction);
    }

    application.setTransactions(transactions);

    applicationRepository.save(application);
    return ApplicationResponse.builder()
        .applicationId(application.getId())
        .transactionIds(
            transactions.stream().map(TransactionEntity::getId).collect(Collectors.toList()))
        .build();
  }

  TransactionEntity buildTransactionEntity(
      TransactionType type,
      Object metadata,
      UUID customerId,
      Set<String> assignees,
      int requiredApprovals) {

    var entity =
        TransactionEntity.builder()
            .PIC(RequestUtil.getUserId())
            .metadata(metadata)
            .status(ApproveStatus.WAIT)
            .type(type)
            .customerId(customerId)
            .requiredApprovals(requiredApprovals)
            .createdAt(ZonedDateTime.now())
            .metadata(metadata)
            .build();

    if (CollectionUtils.isEmpty(assignees)) {
      assignees = Set.of(RequestUtil.getUserId());
    }
    List<Approve> approves =
        employeeService.findByUserIdIn(assignees).stream()
            .map(
                employee ->
                    (Approve)
                        Approve.builder()
                            .approver(employee)
                            .status(ApproveStatus.WAIT)
                            .transaction(entity)
                            .build())
            .toList();

    entity.setApproves(approves);
    return entity;
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
    ApplicationDTO response = applicationMapper.toDTO(application);
    List<TransactionEntity> transactions = application.getTransactions();

    if (CollectionUtils.isNotEmpty(transactions)) {
      boolean fullApproved =
          transactions.stream()
              .map(mapper::toDomain)
              .collect(
                  Collectors.toMap(
                      TransactionDTO::getType,
                      com.tsoftware.qtd.dto.transaction.TransactionDTO::isApproved,
                      (a, b) -> a || b))
              .values()
              .stream()
              .allMatch(Boolean.TRUE::equals);
      boolean fulDocument =
          Objects.nonNull(application.getFinancialInfo())
              && Objects.nonNull(application.getLoanPlan())
              && Objects.nonNull(application.getLoanRequest());
      response.setCanSign(fullApproved && fulDocument);
    }

    return response;
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
