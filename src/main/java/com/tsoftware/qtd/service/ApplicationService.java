package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.dto.loan.SignRequestDetail;
import com.tsoftware.qtd.dto.loan.SignResponse;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {
  ApplicationResponse create(UUID customerId, ApplicationDTO applicationRequest) throws Exception;

  ApplicationResponse update(UUID id, ApplicationDTO applicationRequest);

  void delete(UUID id);

  ApplicationDTO getById(UUID id);

  List<ApplicationResponse> getAll();

  void createOrUpdateFinancialInfo(UUID applicationId, FinancialInfoDTO data);

  void createOrUpdateLoanPlan(UUID applicationId, LoanPlanDTO loanPlanDTO);

  void createOrUpdateLoanRequest(UUID applicationId, LoanRequestDTO loanRequestDTO);

  SignResponse sign(UUID id, SignRequestDetail signRequestDetail);
}
