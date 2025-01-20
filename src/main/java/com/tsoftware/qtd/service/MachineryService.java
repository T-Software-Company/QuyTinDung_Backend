package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.MachineryRequest;
import com.tsoftware.qtd.entity.Machinery;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.MachineryMapper;
import com.tsoftware.qtd.repository.MachineryRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MachineryService {

  private final MachineryRepository machineryRepository;

  private final MachineryMapper machineryMapper;

  public MachineryRequest create(MachineryRequest machineryRequest) {
    Machinery machinery = machineryMapper.toEntity(machineryRequest);
    return machineryMapper.toDTO(machineryRepository.save(machinery));
  }

  public MachineryRequest update(UUID id, MachineryRequest machineryRequest) {
    Machinery machinery =
        machineryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Machinery not found"));
    machineryMapper.updateEntity(machineryRequest, machinery);
    return machineryMapper.toDTO(machineryRepository.save(machinery));
  }

  public void delete(UUID id) {
    machineryRepository.deleteById(id);
  }

  public MachineryRequest getById(UUID id) {
    Machinery machinery =
        machineryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Machinery not found"));
    return machineryMapper.toDTO(machinery);
  }

  public List<MachineryRequest> getAll() {
    return machineryRepository.findAll().stream()
        .map(machineryMapper::toDTO)
        .collect(Collectors.toList());
  }
}
