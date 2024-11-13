package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.CustomerRequest;
import java.util.List;

public interface CustomerService {
  CustomerRequest create(CustomerRequest customerRequest);

  CustomerRequest update(Long id, CustomerRequest customerRequest);

  void delete(Long id);

  CustomerRequest getById(Long id);

  List<CustomerRequest> getAll();
}
