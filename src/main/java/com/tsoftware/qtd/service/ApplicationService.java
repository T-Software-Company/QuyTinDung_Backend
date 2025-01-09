package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.entity.Application;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ApplicationService {
  ApplicationResponse create(UUID customerId) throws Exception;

  ApplicationResponse update(UUID id, ApplicationDTO applicationRequest);

  void delete(UUID id);

  ApplicationDTO getById(UUID id);

  Page<ApplicationResponse> getAll(Specification<Application> spec, Pageable page);

  void createOrUpdateFinancialInfo(UUID applicationId, FinancialInfoDTO data);

  void createOrUpdateLoanPlan(UUID applicationId, LoanPlanDTO loanPlanDTO);

  void createOrUpdateLoanRequest(UUID applicationId, LoanRequestDTO loanRequestDTO);
}
