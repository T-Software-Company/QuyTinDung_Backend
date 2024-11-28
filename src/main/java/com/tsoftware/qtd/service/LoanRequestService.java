package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import java.util.List;

public interface LoanRequestService {
  LoanRequestResponse create(LoanRequestRequest loanrequestRequest, Long creditId);

  LoanRequestResponse update(Long id, LoanRequestRequest loanrequestRequest);

  void delete(Long id);

  LoanRequestResponse getById(Long id);

  List<LoanRequestResponse> getAll();
}
