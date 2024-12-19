package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.customer.CustomerDTO;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CustomerService {
  CustomerResponse create(CustomerDTO customerDTO) throws Exception;

  CustomerResponse update(UUID id, CustomerDTO customerDTO);

  void delete(UUID id);

  CustomerDTO getById(UUID id);

  PageResponse<CustomerDTO> getAll(Specification<Customer> spec, Pageable page);
}
