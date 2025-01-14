package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionResponse;
import java.util.List;
import java.util.UUID;

public interface LoanRequestService {
  WorkflowTransactionResponse request(LoanRequestRequest loanRequestRequest, UUID applicationId);

  LoanRequestResponse create(LoanRequestRequest loanRequestRequest, UUID applicationId);

  LoanRequestResponse update(UUID id, LoanRequestRequest loanRequestRequest);

  void delete(UUID id);

  LoanRequestResponse getById(UUID id);

  List<LoanRequestResponse> getAll();
}
