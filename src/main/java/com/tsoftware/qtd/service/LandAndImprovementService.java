package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LandAndImprovementDto;
import com.tsoftware.qtd.entity.LandAndImprovement;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LandAndImprovementMapper;
import com.tsoftware.qtd.repository.LandAndImprovementRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LandAndImprovementService {

  private final LandAndImprovementRepository landandimprovementRepository;

  private final LandAndImprovementMapper landandimprovementMapper;

  public LandAndImprovementDto create(LandAndImprovementDto landandimprovementDto) {
    LandAndImprovement landandimprovement =
        landandimprovementMapper.toEntity(landandimprovementDto);
    return landandimprovementMapper.toDTO(landandimprovementRepository.save(landandimprovement));
  }

  public LandAndImprovementDto update(UUID id, LandAndImprovementDto landandimprovementDto) {
    LandAndImprovement landandimprovement =
        landandimprovementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAndImprovement not found"));
    landandimprovementMapper.updateEntity(landandimprovementDto, landandimprovement);
    return landandimprovementMapper.toDTO(landandimprovementRepository.save(landandimprovement));
  }

  public void delete(UUID id) {
    landandimprovementRepository.deleteById(id);
  }

  public LandAndImprovementDto getById(UUID id) {
    LandAndImprovement landandimprovement =
        landandimprovementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAndImprovement not found"));
    return landandimprovementMapper.toDTO(landandimprovement);
  }

  public List<LandAndImprovementDto> getAll() {
    return landandimprovementRepository.findAll().stream()
        .map(landandimprovementMapper::toDTO)
        .collect(Collectors.toList());
  }
}
