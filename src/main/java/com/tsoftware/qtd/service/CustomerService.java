package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.CustomerDto;
import java.util.List;

public interface CustomerService {
  CustomerDto create(CustomerDto customerDto);

  CustomerDto update(Long id, CustomerDto customerDto);

  void delete(Long id);

  CustomerDto getById(Long id);

  List<CustomerDto> getAll();
}
