package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.VehicleDTO;
import com.tsoftware.qtd.entity.Vehicle;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.VehicleMapper;
import com.tsoftware.qtd.repository.VehicleRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleService {

  private final VehicleRepository vehicleRepository;

  private final VehicleMapper vehicleMapper;

  public VehicleDTO create(VehicleDTO vehicleDTO) {
    Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
    return vehicleMapper.toDTO(vehicleRepository.save(vehicle));
  }

  public VehicleDTO update(UUID id, VehicleDTO vehicleDTO) {
    Vehicle vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    vehicleMapper.updateEntity(vehicleDTO, vehicle);
    return vehicleMapper.toDTO(vehicleRepository.save(vehicle));
  }

  public void delete(UUID id) {
    vehicleRepository.deleteById(id);
  }

  public VehicleDTO getById(UUID id) {
    Vehicle vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    return vehicleMapper.toDTO(vehicle);
  }

  public List<VehicleDTO> getAll() {
    return vehicleRepository.findAll().stream()
        .map(vehicleMapper::toDTO)
        .collect(Collectors.toList());
  }
}
