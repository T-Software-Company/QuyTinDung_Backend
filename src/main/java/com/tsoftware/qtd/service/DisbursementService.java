package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.DisbursementDto;
import java.util.List;

public interface DisbursementService {
  DisbursementDto create(DisbursementDto disbursementDto);

  DisbursementDto update(Long id, DisbursementDto disbursementDto);

  void delete(Long id);

  DisbursementDto getById(Long id);

  List<DisbursementDto> getAll();
}
