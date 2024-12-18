package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.DisbursementDto;
import java.util.List;
import java.util.UUID;

public interface DisbursementService {
  DisbursementDto create(DisbursementDto disbursementDto);

  DisbursementDto update(UUID id, DisbursementDto disbursementDto);

  void delete(UUID id);

  DisbursementDto getById(UUID id);

  List<DisbursementDto> getAll();
}
