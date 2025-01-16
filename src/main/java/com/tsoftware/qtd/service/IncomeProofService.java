package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.IncomeProofDto;
import com.tsoftware.qtd.entity.IncomeProof;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.IncomeProofMapper;
import com.tsoftware.qtd.repository.IncomeProofRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeProofService {

  private final IncomeProofRepository incomeproofRepository;

  private final IncomeProofMapper incomeproofMapper;

  public IncomeProofDto create(IncomeProofDto incomeproofDto) {
    IncomeProof incomeproof = incomeproofMapper.toEntity(incomeproofDto);
    return incomeproofMapper.toDTO(incomeproofRepository.save(incomeproof));
  }

  public IncomeProofDto update(UUID id, IncomeProofDto incomeproofDto) {
    IncomeProof incomeproof =
        incomeproofRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("IncomeProof not found"));
    incomeproofMapper.updateEntity(incomeproofDto, incomeproof);
    return incomeproofMapper.toDTO(incomeproofRepository.save(incomeproof));
  }

  public void delete(UUID id) {
    incomeproofRepository.deleteById(id);
  }

  public IncomeProofDto getById(UUID id) {
    IncomeProof incomeproof =
        incomeproofRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("IncomeProof not found"));
    return incomeproofMapper.toDTO(incomeproof);
  }

  public List<IncomeProofDto> getAll() {
    return incomeproofRepository.findAll().stream()
        .map(incomeproofMapper::toDTO)
        .collect(Collectors.toList());
  }
}
