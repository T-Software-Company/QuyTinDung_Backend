package com.tsoftware.qtd.service;

import com.tsoftware.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.commonlib.model.Transaction;
import com.tsoftware.qtd.constants.EnumType.TransactionStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.request.CreateCustomerRequest;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
  final TransactionExecutorRegistry registry;
  final TransactionRepository repository;
  final DtoMapper mapper;

  public Transaction createCustomerRequest(CreateCustomerRequest request) {
    Transaction createCustomerTransaction = new Transaction();
    createCustomerTransaction.setMetadata(request);
    createCustomerTransaction.setType(TransactionType.CREATE_CUSTOMER.name());
    createCustomerTransaction.setStatus(TransactionStatus.CREATED.name());
    repository.save(mapper.toEntity(createCustomerTransaction));
    return createCustomerTransaction;
  }

  public Object approve(Long id) {
    Transaction transaction = repository.findById(id).map(mapper::toDomain).orElseThrow();
    return registry.getExecutor(transaction.getType()).execute(transaction);
  }
}
