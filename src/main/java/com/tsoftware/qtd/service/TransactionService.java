package com.tsoftware.qtd.service;

import com.tsoftware.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.qtd.dto.Transaction;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.TransactionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
  final TransactionExecutorRegistry registry;
  final TransactionRepository repository;
  final DtoMapper mapper;

  public Object approve(UUID id) {
    Transaction transaction =
        repository
            .findById(id)
            .map(mapper::toDomain)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    return registry.getExecutor(transaction.getType()).execute(transaction);
  }
}
