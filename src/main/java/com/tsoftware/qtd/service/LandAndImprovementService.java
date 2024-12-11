package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LandAndImprovementDto;
import java.util.List;
import java.util.UUID;

public interface LandAndImprovementService {
  LandAndImprovementDto create(LandAndImprovementDto landandimprovementDto);

  LandAndImprovementDto update(UUID id, LandAndImprovementDto landandimprovementDto);

  void delete(UUID id);

  LandAndImprovementDto getById(UUID id);

  List<LandAndImprovementDto> getAll();
}
