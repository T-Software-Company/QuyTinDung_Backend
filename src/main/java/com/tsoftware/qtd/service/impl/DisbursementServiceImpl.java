package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.DisbursementDto;
import com.tsoftware.qtd.entity.Disbursement;
import com.tsoftware.qtd.mapper.DisbursementMapper;
import com.tsoftware.qtd.repository.DisbursementRepository;
import com.tsoftware.qtd.service.DisbursementService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisbursementServiceImpl implements DisbursementService {

  @Autowired private DisbursementRepository disbursementRepository;

  @Autowired private DisbursementMapper disbursementMapper;

  @Override
  public DisbursementDto create(DisbursementDto disbursementDto) {
    Disbursement disbursement = disbursementMapper.toEntity(disbursementDto);
    return disbursementMapper.toDto(disbursementRepository.save(disbursement));
  }

  @Override
  public DisbursementDto update(Long id, DisbursementDto disbursementDto) {
    Disbursement disbursement =
        disbursementRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Disbursement not found"));
    disbursementMapper.updateEntity(disbursementDto, disbursement);
    return disbursementMapper.toDto(disbursementRepository.save(disbursement));
  }

  @Override
  public void delete(Long id) {
    disbursementRepository.deleteById(id);
  }

  @Override
  public DisbursementDto getById(Long id) {
    Disbursement disbursement =
        disbursementRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Disbursement not found"));
    return disbursementMapper.toDto(disbursement);
  }

  @Override
  public List<DisbursementDto> getAll() {
    return disbursementRepository.findAll().stream()
        .map(disbursementMapper::toDto)
        .collect(Collectors.toList());
  }
}
