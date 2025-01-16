package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.entity.Disbursement;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.DisbursementMapper;
import com.tsoftware.qtd.repository.DisbursementRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DisbursementService {

  private final DisbursementRepository disbursementRepository;

  private final DisbursementMapper disbursementMapper;

  public DisbursementDTO create(DisbursementDTO disbursementDto) {
    Disbursement disbursement = disbursementMapper.toEntity(disbursementDto);
    return disbursementMapper.toDTO(disbursementRepository.save(disbursement));
  }

  public DisbursementDTO update(UUID id, DisbursementDTO disbursementDto) {
    Disbursement disbursement =
        disbursementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DisbursementDto not found"));
    disbursementMapper.updateEntity(disbursementDto, disbursement);
    return disbursementMapper.toDTO(disbursementRepository.save(disbursement));
  }

  public void delete(UUID id) {
    disbursementRepository.deleteById(id);
  }

  public DisbursementDTO getById(UUID id) {
    Disbursement disbursement =
        disbursementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DisbursementDto not found"));
    return disbursementMapper.toDTO(disbursement);
  }

  public List<DisbursementDTO> getAll() {
    return disbursementRepository.findAll().stream()
        .map(disbursementMapper::toDTO)
        .collect(Collectors.toList());
  }
}
