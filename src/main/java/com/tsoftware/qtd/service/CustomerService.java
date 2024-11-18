package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import java.util.List;

public interface CustomerService {
  CustomerResponse create(CustomerRequest customerRequest) throws Exception;

  CustomerResponse update(Long id, CustomerRequest customerRequest);

  void delete(Long id);

  CustomerResponse getById(Long id);

  List<CustomerResponse> getAll();
}
