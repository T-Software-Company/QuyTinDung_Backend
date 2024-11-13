package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LandAndImprovementDto;
import java.util.List;

public interface LandAndImprovementService {
  LandAndImprovementDto create(LandAndImprovementDto landandimprovementDto);

  LandAndImprovementDto update(Long id, LandAndImprovementDto landandimprovementDto);

  void delete(Long id);

  LandAndImprovementDto getById(Long id);

  List<LandAndImprovementDto> getAll();
}
