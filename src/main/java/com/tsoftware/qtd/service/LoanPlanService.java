package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import java.util.List;
import java.util.UUID;

public interface LoanPlanService {
  LoanPlanResponse create(LoanPlanDTO loanplanDTO, UUID creditId);

  LoanPlanResponse update(UUID id, LoanPlanDTO loanplanDTO);

  void delete(UUID id);

  LoanPlanResponse getById(UUID id);

  List<LoanPlanResponse> getAll();
}
