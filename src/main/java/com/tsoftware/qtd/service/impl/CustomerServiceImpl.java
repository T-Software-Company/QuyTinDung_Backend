package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.entity.Customer;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CustomerMapper;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.service.CustomerService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private CustomerMapper customerMapper;

  @Override
  public CustomerRequest create(CustomerRequest customerRequest) {
    Customer customer = customerMapper.toEntity(customerRequest);
    return customerMapper.toDto(customerRepository.save(customer));
  }

  @Override
  public CustomerRequest update(Long id, CustomerRequest customerRequest) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    customerMapper.updateEntity(customerRequest, customer);
    return customerMapper.toDto(customerRepository.save(customer));
  }

  @Override
  public void delete(Long id) {
    customerRepository.deleteById(id);
  }

  @Override
  public CustomerRequest getById(Long id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    return customerMapper.toDto(customer);
  }

  @Override
  public List<CustomerRequest> getAll() {
    return customerRepository.findAll().stream()
        .map(customerMapper::toDto)
        .collect(Collectors.toList());
  }
}
