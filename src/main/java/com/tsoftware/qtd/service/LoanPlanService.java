package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import java.util.List;
import java.util.UUID;

public interface LoanPlanService {
  LoanPlanResponse create(LoanPlanRequest loanplanRequest, UUID applicationId);

  LoanPlanResponse update(UUID id, LoanPlanRequest loanplanRequest);

  void delete(UUID id);

  LoanPlanResponse getById(UUID id);

  List<LoanPlanResponse> getAll();
}
