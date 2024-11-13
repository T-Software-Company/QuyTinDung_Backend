package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.MachineryDto;
import java.util.List;

public interface MachineryService {
  MachineryDto create(MachineryDto machineryDto);

  MachineryDto update(Long id, MachineryDto machineryDto);

  void delete(Long id);

  MachineryDto getById(Long id);

  List<MachineryDto> getAll();
}
