package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.CustomerDto;
import com.tsoftware.qtd.entity.Customer;
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
  public CustomerDto create(CustomerDto customerDto) {
    Customer customer = customerMapper.toEntity(customerDto);
    return customerMapper.toDto(customerRepository.save(customer));
  }

  @Override
  public CustomerDto update(Long id, CustomerDto customerDto) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    customerMapper.updateEntity(customerDto, customer);
    return customerMapper.toDto(customerRepository.save(customer));
  }

  @Override
  public void delete(Long id) {
    customerRepository.deleteById(id);
  }

  @Override
  public CustomerDto getById(Long id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    return customerMapper.toDto(customer);
  }

  @Override
  public List<CustomerDto> getAll() {
    return customerRepository.findAll().stream()
        .map(customerMapper::toDto)
        .collect(Collectors.toList());
  }
}
