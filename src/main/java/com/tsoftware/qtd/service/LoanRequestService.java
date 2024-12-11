package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.LoanRequestDTO;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import java.util.List;
import java.util.UUID;

public interface LoanRequestService {
  LoanRequestResponse create(LoanRequestDTO loanrequestRequestDTO, UUID creditId);

  LoanRequestResponse update(UUID id, LoanRequestDTO loanrequestRequestDTO);

  void delete(UUID id);

  LoanRequestResponse getById(UUID id);

  List<LoanRequestResponse> getAll();
}
