package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApplicationMapper;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.mapper.WorkflowTransactionMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import com.tsoftware.qtd.repository.WorkflowTransactionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanRequestService {

  private final LoanRequestRepository loanrequestRepository;
  private final LoanRequestMapper loanrequestMapper;
  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;
  private final WorkflowTransactionMapper workflowTransactionMapper;
  private final WorkflowTransactionRepository workflowTransactionRepository;
  private final WorkflowTransactionService workflowTransactionService;

  public WorkflowTransactionResponse request(LoanRequestRequest loanRequestRequest) {
    var type = TransactionType.CREATE_LOAN_REQUEST;
    var exists =
        workflowTransactionRepository.existsByApplicationIdAndType(
            UUID.fromString(loanRequestRequest.getApplication().getId()), type);
    if (exists) {
      throw new CommonException(
          ErrorType.HAS_APPLICATION_IN_PROGRESS,
          "application: " + loanRequestRequest.getApplication().getId(),
          type.name());
    }
    var transaction =
        WorkflowTransactionDTO.builder()
            .application(applicationMapper.toDTO(loanRequestRequest.getApplication()))
            .type(type)
            .status(ApproveStatus.WAIT)
            .metadata(loanRequestRequest)
            .build();
    workflowTransactionService.calculateApproves(transaction, type);
    var entity = workflowTransactionMapper.toEntity(transaction);
    Optional.ofNullable(entity.getGroupApproves())
        .ifPresent(stef -> stef.forEach(groupApprove -> groupApprove.setTransaction(entity)));
    Optional.ofNullable(entity.getRoleApproves())
        .ifPresent(stef -> stef.forEach(roleApprove -> roleApprove.setTransaction(entity)));
    Optional.ofNullable(entity.getApproves())
        .ifPresent(stef -> stef.forEach(approve -> approve.setTransaction(entity)));
    var saved = workflowTransactionRepository.save(entity);
    return workflowTransactionMapper.toResponse(saved);
  }

  public LoanRequestResponse create(LoanRequestRequest loanRequestRequest, UUID applicationId) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestMapper.toEntity(loanRequestRequest);
    Application application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    loanrequest.setApplication(application);
    application.setAmount(loanrequest.getAmount());
    application.setLoanSecurityType(loanrequest.getLoanSecurityType());
    applicationRepository.save(application);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  public LoanRequestResponse update(UUID id, LoanRequestRequest loanRequestRequest) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    loanrequestMapper.updateEntity(loanRequestRequest, loanrequest);
    var credit = loanrequest.getApplication();
    credit.setAmount(loanrequest.getAmount());
    credit.setLoanSecurityType(loanrequest.getLoanSecurityType());
    applicationRepository.save(credit);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  public void delete(UUID id) {
    loanrequestRepository.deleteById(id);
  }

  public LoanRequestResponse getById(UUID id) {
    com.tsoftware.qtd.entity.LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    return loanrequestMapper.toResponse(loanrequest);
  }

  public List<LoanRequestResponse> getAll() {
    return loanrequestRepository.findAll().stream()
        .map(loanrequestMapper::toResponse)
        .collect(Collectors.toList());
  }
}
