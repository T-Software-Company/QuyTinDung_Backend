package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.IncomeProofDto;
import java.util.List;
import java.util.UUID;

public interface IncomeProofService {
  IncomeProofDto create(IncomeProofDto incomeproofDto);

  IncomeProofDto update(UUID id, IncomeProofDto incomeproofDto);

  void delete(UUID id);

  IncomeProofDto getById(UUID id);

  List<IncomeProofDto> getAll();
}
