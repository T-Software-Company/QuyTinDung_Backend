package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.event.LoanPlanSubmittedEvent;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanPlanMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.ApprovalProcessRepository;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanPlanService {

  private final LoanPlanRepository loanplanRepository;
  private final LoanPlanMapper loanplanMapper;
  private final ApprovalProcessService approvalProcessService;
  private final ApplicationRepository applicationRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final LoanRequestRepository loanRequestRepository;
  private final ApprovalProcessRepository approvalProcessRepository;
  private final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

  public ApprovalProcessResponse request(LoanPlanRequest loanPlanRequest) {
    this.validRequest(loanPlanRequest);
    var result =
        approvalProcessService.create(
            loanPlanRequest, loanPlanRequest.getApplication(), ProcessType.CREATE_LOAN_PLAN);
    applicationEventPublisher.publishEvent(new LoanPlanSubmittedEvent(this, result));
    return result;
  }

  public LoanPlanResponse create(LoanPlanRequest loanplanRequest, UUID applicationId) {
    LoanPlan loanplan = loanplanMapper.toEntity(loanplanRequest);
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "Application not found: " + applicationId));
    application.setInterestRate(loanplan.getInterestRate());
    application.setLoanTerm(loanplan.getLoanTerm());
    applicationRepository.save(application);
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

  private void validRequest(LoanPlanRequest loanPlanRequest) {
    var applicationId = loanPlanRequest.getApplication().getId();
    loanRequestRepository
        .findByApplicationId(UUID.fromString(applicationId))
        .ifPresent(
            l -> {
              if (loanPlanRequest.getProposedLoanAmount().compareTo(l.getAmount()) < -0) {
                throw new CommonException(
                    ErrorType.CHECKSUM_INVALID,
                    "Proposed loan amount ("
                        + decimalFormat.format(loanPlanRequest.getProposedLoanAmount())
                        + ") does not match the expected amount ("
                        + decimalFormat.format(l.getAmount())
                        + ")");
              }
            });
    approvalProcessRepository
        .findByApplicationIdAndType(UUID.fromString(applicationId), ProcessType.CREATE_LOAN_REQUEST)
        .ifPresent(
            a -> {
              var loanRequest = JsonParser.convert(a.getMetadata(), LoanRequestRequest.class);
              if (loanPlanRequest.getProposedLoanAmount().compareTo(loanRequest.getAmount()) < 0) {
                throw new CommonException(
                    ErrorType.CHECKSUM_INVALID,
                    "Proposed loan amount ("
                        + decimalFormat.format(loanPlanRequest.getProposedLoanAmount())
                        + ") does not match the expected amount ("
                        + decimalFormat.format(loanRequest.getAmount())
                        + ")");
              }
            });
  }
}
