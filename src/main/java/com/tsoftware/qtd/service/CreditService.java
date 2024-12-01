package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import java.util.List;
import java.util.UUID;

public interface CreditService {
  CreditResponse create(UUID customerId) throws Exception;

  CreditResponse update(UUID id, CreditRequest creditRequest);

  void delete(UUID id);

  CreditResponse getById(UUID id);

  List<CreditResponse> getAll();
}
