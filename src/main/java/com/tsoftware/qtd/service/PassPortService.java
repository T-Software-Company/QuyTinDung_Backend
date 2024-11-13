package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.PassPortDto;
import java.util.List;

public interface PassPortService {
  PassPortDto create(PassPortDto passportDto);

  PassPortDto update(Long id, PassPortDto passportDto);

  void delete(Long id);

  PassPortDto getById(Long id);

  List<PassPortDto> getAll();
}
