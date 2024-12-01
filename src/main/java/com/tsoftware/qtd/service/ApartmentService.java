package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.ApartmentDto;
import java.util.List;
import java.util.UUID;

public interface ApartmentService {
  ApartmentDto create(ApartmentDto apartmentDto);

  ApartmentDto update(UUID id, ApartmentDto apartmentDto);

  void delete(UUID id);

  ApartmentDto getById(UUID id);

  List<ApartmentDto> getAll();
}
