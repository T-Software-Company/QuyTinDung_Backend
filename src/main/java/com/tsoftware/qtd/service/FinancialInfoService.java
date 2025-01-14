package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;

public interface FinancialInfoService {
  FinancialInfoResponse create(FinancialInfoRequest request);
}
