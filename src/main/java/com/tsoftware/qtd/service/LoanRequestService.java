package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import java.util.List;
import java.util.UUID;

public interface LoanRequestService {
  LoanRequestResponse create(LoanRequestDTO loanRequestDTO, UUID creditId);

  LoanRequestResponse update(UUID id, LoanRequestDTO loanRequestDTO);

  void delete(UUID id);

  LoanRequestResponse getById(UUID id);

  List<LoanRequestResponse> getAll();
}
