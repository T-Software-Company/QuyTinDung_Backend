package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import java.util.List;

public interface CreditService {
  CreditResponse create(CreditRequest creditRequest);

  CreditResponse update(Long id, CreditRequest creditRequest);

  void delete(Long id);

  CreditResponse getById(Long id);

  List<CreditResponse> getAll();
}
