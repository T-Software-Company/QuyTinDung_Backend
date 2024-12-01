package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import java.util.List;
import java.util.UUID;

public interface LoanRequestService {
  LoanRequestResponse create(LoanRequestRequest loanrequestRequest, UUID creditId);

  LoanRequestResponse update(UUID id, LoanRequestRequest loanrequestRequest);

  void delete(UUID id);

  LoanRequestResponse getById(UUID id);

  List<LoanRequestResponse> getAll();
}
