package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.ApartmentDto;
import java.util.List;

public interface ApartmentService {
  ApartmentDto create(ApartmentDto apartmentDto);

  ApartmentDto update(Long id, ApartmentDto apartmentDto);

  void delete(Long id);

  ApartmentDto getById(Long id);

  List<ApartmentDto> getAll();
}
