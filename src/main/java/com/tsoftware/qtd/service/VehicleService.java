package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.VehicleDto;
import java.util.List;

public interface VehicleService {
  VehicleDto create(VehicleDto vehicleDto);

  VehicleDto update(Long id, VehicleDto vehicleDto);

  void delete(Long id);

  VehicleDto getById(Long id);

  List<VehicleDto> getAll();
}
