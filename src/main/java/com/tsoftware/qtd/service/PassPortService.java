package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.PassPortDto;
import java.util.List;
import java.util.UUID;

public interface PassPortService {
  PassPortDto create(PassPortDto passportDto);

  PassPortDto update(UUID id, PassPortDto passportDto);

  void delete(UUID id);

  PassPortDto getById(UUID id);

  List<PassPortDto> getAll();
}
