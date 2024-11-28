package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.LoanPlanRequest;
import com.tsoftware.qtd.dto.credit.LoanPlanResponse;
import java.util.List;

public interface LoanPlanService {
  LoanPlanResponse create(LoanPlanRequest loanplanRequest, Long creditId);

  LoanPlanResponse update(Long id, LoanPlanRequest loanplanRequest);

  void delete(Long id);

  LoanPlanResponse getById(Long id);

  List<LoanPlanResponse> getAll();
}
