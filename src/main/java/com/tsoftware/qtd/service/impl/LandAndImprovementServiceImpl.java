package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.LandAndImprovementDto;
import com.tsoftware.qtd.entity.LandAndImprovement;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LandAndImprovementMapper;
import com.tsoftware.qtd.repository.LandAndImprovementRepository;
import com.tsoftware.qtd.service.LandAndImprovementService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandAndImprovementServiceImpl implements LandAndImprovementService {

  @Autowired private LandAndImprovementRepository landandimprovementRepository;

  @Autowired private LandAndImprovementMapper landandimprovementMapper;

  @Override
  public LandAndImprovementDto create(LandAndImprovementDto landandimprovementDto) {
    LandAndImprovement landandimprovement =
        landandimprovementMapper.toEntity(landandimprovementDto);
    return landandimprovementMapper.toDto(landandimprovementRepository.save(landandimprovement));
  }

  @Override
  public LandAndImprovementDto update(Long id, LandAndImprovementDto landandimprovementDto) {
    LandAndImprovement landandimprovement =
        landandimprovementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAndImprovement not found"));
    landandimprovementMapper.updateEntity(landandimprovementDto, landandimprovement);
    return landandimprovementMapper.toDto(landandimprovementRepository.save(landandimprovement));
  }

  @Override
  public void delete(Long id) {
    landandimprovementRepository.deleteById(id);
  }

  @Override
  public LandAndImprovementDto getById(Long id) {
    LandAndImprovement landandimprovement =
        landandimprovementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAndImprovement not found"));
    return landandimprovementMapper.toDto(landandimprovement);
  }

  @Override
  public List<LandAndImprovementDto> getAll() {
    return landandimprovementRepository.findAll().stream()
        .map(landandimprovementMapper::toDto)
        .collect(Collectors.toList());
  }
}
