package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.DisbursementDTO;
import java.util.List;
import java.util.UUID;

public interface DisbursementService {
  DisbursementDTO create(DisbursementDTO disbursementDto);

  DisbursementDTO update(UUID id, DisbursementDTO disbursementDto);

  void delete(UUID id);

  DisbursementDTO getById(UUID id);

  List<DisbursementDTO> getAll();
}
