package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LandAndImprovementRequest;
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

  public LandAndImprovementRequest create(LandAndImprovementRequest landandimprovementRequest) {
    LandAndImprovement landandimprovement =
        landandimprovementMapper.toEntity(landandimprovementRequest);
    return landandimprovementMapper.toDTO(landandimprovementRepository.save(landandimprovement));
  }

  public LandAndImprovementRequest update(
      UUID id, LandAndImprovementRequest landandimprovementRequest) {
    LandAndImprovement landandimprovement =
        landandimprovementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAndImprovement not found"));
    landandimprovementMapper.updateEntity(landandimprovementRequest, landandimprovement);
    return landandimprovementMapper.toDTO(landandimprovementRepository.save(landandimprovement));
  }

  public void delete(UUID id) {
    landandimprovementRepository.deleteById(id);
  }

  public LandAndImprovementRequest getById(UUID id) {
    LandAndImprovement landandimprovement =
        landandimprovementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LandAndImprovement not found"));
    return landandimprovementMapper.toDTO(landandimprovement);
  }

  public List<LandAndImprovementRequest> getAll() {
    return landandimprovementRepository.findAll().stream()
        .map(landandimprovementMapper::toDTO)
        .collect(Collectors.toList());
  }
}
