package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.CreditDto;
import java.util.List;

public interface CreditService {
  CreditDto create(CreditDto creditDto);

  CreditDto update(Long id, CreditDto creditDto);

  void delete(Long id);

  CreditDto getById(Long id);

  List<CreditDto> getAll();
}
