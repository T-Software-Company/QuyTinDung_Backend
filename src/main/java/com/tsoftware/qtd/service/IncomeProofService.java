package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.IncomeProofDto;
import java.util.List;

public interface IncomeProofService {
  IncomeProofDto create(IncomeProofDto incomeproofDto);

  IncomeProofDto update(Long id, IncomeProofDto incomeproofDto);

  void delete(Long id);

  IncomeProofDto getById(Long id);

  List<IncomeProofDto> getAll();
}
