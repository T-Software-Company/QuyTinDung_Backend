package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.IncomeProofDto;
import com.tsoftware.qtd.entity.IncomeProof;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.IncomeProofMapper;
import com.tsoftware.qtd.repository.IncomeProofRepository;
import com.tsoftware.qtd.service.IncomeProofService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeProofServiceImpl implements IncomeProofService {

  private final IncomeProofRepository incomeproofRepository;

  private final IncomeProofMapper incomeproofMapper;

  @Override
  public IncomeProofDto create(IncomeProofDto incomeproofDto) {
    IncomeProof incomeproof = incomeproofMapper.toEntity(incomeproofDto);
    return incomeproofMapper.toDto(incomeproofRepository.save(incomeproof));
  }

  @Override
  public IncomeProofDto update(UUID id, IncomeProofDto incomeproofDto) {
    IncomeProof incomeproof =
        incomeproofRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("IncomeProof not found"));
    incomeproofMapper.updateEntity(incomeproofDto, incomeproof);
    return incomeproofMapper.toDto(incomeproofRepository.save(incomeproof));
  }

  @Override
  public void delete(UUID id) {
    incomeproofRepository.deleteById(id);
  }

  @Override
  public IncomeProofDto getById(UUID id) {
    IncomeProof incomeproof =
        incomeproofRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("IncomeProof not found"));
    return incomeproofMapper.toDto(incomeproof);
  }

  @Override
  public List<IncomeProofDto> getAll() {
    return incomeproofRepository.findAll().stream()
        .map(incomeproofMapper::toDto)
        .collect(Collectors.toList());
  }
}
