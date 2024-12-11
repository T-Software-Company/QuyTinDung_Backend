package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.CCCDDto;
import java.util.List;
import java.util.UUID;

public interface CCCDService {
  CCCDDto create(CCCDDto cccdDto);

  CCCDDto update(UUID id, CCCDDto cccdDto);

  void delete(UUID id);

  CCCDDto getById(UUID id);

  List<CCCDDto> getAll();
}
