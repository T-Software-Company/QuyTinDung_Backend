package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.MachineryDto;
import java.util.List;
import java.util.UUID;

public interface MachineryService {
  MachineryDto create(MachineryDto machineryDto);

  MachineryDto update(UUID id, MachineryDto machineryDto);

  void delete(UUID id);

  MachineryDto getById(UUID id);

  List<MachineryDto> getAll();
}
