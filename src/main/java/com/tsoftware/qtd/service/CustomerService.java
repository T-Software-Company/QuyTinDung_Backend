package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
  CustomerResponse create(CustomerRequest customerRequest) throws Exception;

  CustomerResponse update(UUID id, CustomerRequest customerRequest);

  void delete(UUID id);

  CustomerResponse getById(UUID id);

  List<CustomerResponse> getAll();
}
