package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.MachineryDto;
import com.tsoftware.qtd.entity.Machinery;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.MachineryMapper;
import com.tsoftware.qtd.repository.MachineryRepository;
import com.tsoftware.qtd.service.MachineryService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MachineryServiceImpl implements MachineryService {

  private final MachineryRepository machineryRepository;

  private final MachineryMapper machineryMapper;

  @Override
  public MachineryDto create(MachineryDto machineryDto) {
    Machinery machinery = machineryMapper.toEntity(machineryDto);
    return machineryMapper.toDto(machineryRepository.save(machinery));
  }

  @Override
  public MachineryDto update(UUID id, MachineryDto machineryDto) {
    Machinery machinery =
        machineryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Machinery not found"));
    machineryMapper.updateEntity(machineryDto, machinery);
    return machineryMapper.toDto(machineryRepository.save(machinery));
  }

  @Override
  public void delete(UUID id) {
    machineryRepository.deleteById(id);
  }

  @Override
  public MachineryDto getById(UUID id) {
    Machinery machinery =
        machineryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Machinery not found"));
    return machineryMapper.toDto(machinery);
  }

  @Override
  public List<MachineryDto> getAll() {
    return machineryRepository.findAll().stream()
        .map(machineryMapper::toDto)
        .collect(Collectors.toList());
  }
}
