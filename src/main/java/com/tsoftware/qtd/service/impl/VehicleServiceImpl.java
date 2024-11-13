package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.VehicleDto;
import com.tsoftware.qtd.entity.Vehicle;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.VehicleMapper;
import com.tsoftware.qtd.repository.VehicleRepository;
import com.tsoftware.qtd.service.VehicleService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

  @Autowired private VehicleRepository vehicleRepository;

  @Autowired private VehicleMapper vehicleMapper;

  @Override
  public VehicleDto create(VehicleDto vehicleDto) {
    Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
    return vehicleMapper.toDto(vehicleRepository.save(vehicle));
  }

  @Override
  public VehicleDto update(Long id, VehicleDto vehicleDto) {
    Vehicle vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    vehicleMapper.updateEntity(vehicleDto, vehicle);
    return vehicleMapper.toDto(vehicleRepository.save(vehicle));
  }

  @Override
  public void delete(Long id) {
    vehicleRepository.deleteById(id);
  }

  @Override
  public VehicleDto getById(Long id) {
    Vehicle vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    return vehicleMapper.toDto(vehicle);
  }

  @Override
  public List<VehicleDto> getAll() {
    return vehicleRepository.findAll().stream()
        .map(vehicleMapper::toDto)
        .collect(Collectors.toList());
  }
}
