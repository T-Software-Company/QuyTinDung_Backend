package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.CMNDDto;
import java.util.List;
import java.util.UUID;

public interface CMNDService {
  CMNDDto create(CMNDDto cmndDto);

  CMNDDto update(UUID id, CMNDDto cmndDto);

  void delete(UUID id);

  CMNDDto getById(UUID id);

  List<CMNDDto> getAll();
}
