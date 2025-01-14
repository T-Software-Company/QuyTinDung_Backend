package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.VehicleDto;
import com.tsoftware.qtd.entity.Vehicle;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.VehicleMapper;
import com.tsoftware.qtd.repository.VehicleRepository;
import com.tsoftware.qtd.service.VehicleService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

  private final VehicleRepository vehicleRepository;

  private final VehicleMapper vehicleMapper;

  @Override
  public VehicleDto create(VehicleDto vehicleDto) {
    Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
    return vehicleMapper.toDTO(vehicleRepository.save(vehicle));
  }

  @Override
  public VehicleDto update(UUID id, VehicleDto vehicleDto) {
    Vehicle vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    vehicleMapper.updateEntity(vehicleDto, vehicle);
    return vehicleMapper.toDTO(vehicleRepository.save(vehicle));
  }

  @Override
  public void delete(UUID id) {
    vehicleRepository.deleteById(id);
  }

  @Override
  public VehicleDto getById(UUID id) {
    Vehicle vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    return vehicleMapper.toDTO(vehicle);
  }

  @Override
  public List<VehicleDto> getAll() {
    return vehicleRepository.findAll().stream()
        .map(vehicleMapper::toDTO)
        .collect(Collectors.toList());
  }
}
