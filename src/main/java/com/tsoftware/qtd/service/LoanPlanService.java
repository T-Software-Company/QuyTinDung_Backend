package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.LoanPlanRequest;
import com.tsoftware.qtd.dto.credit.LoanPlanResponse;
import java.util.List;
import java.util.UUID;

public interface LoanPlanService {
  LoanPlanResponse create(LoanPlanRequest loanplanRequest, UUID creditId);

  LoanPlanResponse update(UUID id, LoanPlanRequest loanplanRequest);

  void delete(UUID id);

  LoanPlanResponse getById(UUID id);

  List<LoanPlanResponse> getAll();
}
