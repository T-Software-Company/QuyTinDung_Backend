package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CustomerService {
  CustomerResponse create(CustomerRequest customerRequest) throws Exception;

  CustomerResponse update(UUID id, CustomerRequest customerRequest);

  void delete(UUID id);

  CustomerResponse getById(UUID id);

  PageResponse<CustomerResponse> getAll(Specification<Customer> spec, Pageable page);

  void deletes(List<UUID> ids);

  List<CustomerResponse> getAll();

  CustomerResponse getProfile();
}
