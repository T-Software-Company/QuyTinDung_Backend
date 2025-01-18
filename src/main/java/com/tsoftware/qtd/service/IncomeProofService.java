package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.IncomeProofDTO;
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

  public IncomeProofDTO create(IncomeProofDTO incomeproofDTO) {
    IncomeProof incomeproof = incomeproofMapper.toEntity(incomeproofDTO);
    return incomeproofMapper.toDTO(incomeproofRepository.save(incomeproof));
  }

  public IncomeProofDTO update(UUID id, IncomeProofDTO incomeproofDTO) {
    IncomeProof incomeproof =
        incomeproofRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("IncomeProof not found"));
    incomeproofMapper.updateEntity(incomeproofDTO, incomeproof);
    return incomeproofMapper.toDTO(incomeproofRepository.save(incomeproof));
  }

  public void delete(UUID id) {
    incomeproofRepository.deleteById(id);
  }

  public IncomeProofDTO getById(UUID id) {
    IncomeProof incomeproof =
        incomeproofRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("IncomeProof not found"));
    return incomeproofMapper.toDTO(incomeproof);
  }

  public List<IncomeProofDTO> getAll() {
    return incomeproofRepository.findAll().stream()
        .map(incomeproofMapper::toDTO)
        .collect(Collectors.toList());
  }
}
