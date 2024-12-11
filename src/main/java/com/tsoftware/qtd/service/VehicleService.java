package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.VehicleDto;
import java.util.List;
import java.util.UUID;

public interface VehicleService {
  VehicleDto create(VehicleDto vehicleDto);

  VehicleDto update(UUID id, VehicleDto vehicleDto);

  void delete(UUID id);

  VehicleDto getById(UUID id);

  List<VehicleDto> getAll();
}
