package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.CMNDDto;
import java.util.List;

public interface CMNDService {
  CMNDDto create(CMNDDto cmndDto);

  CMNDDto update(Long id, CMNDDto cmndDto);

  void delete(Long id);

  CMNDDto getById(Long id);

  List<CMNDDto> getAll();
}
