package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.customer.CCCDDto;
import java.util.List;

public interface CCCDService {
  CCCDDto create(CCCDDto cccdDto);

  CCCDDto update(Long id, CCCDDto cccdDto);

  void delete(Long id);

  CCCDDto getById(Long id);

  List<CCCDDto> getAll();
}
