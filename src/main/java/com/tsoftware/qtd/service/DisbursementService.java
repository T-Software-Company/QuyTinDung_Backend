package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.entity.Disbursement;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
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

  public DisbursementDTO create(DisbursementDTO disbursementDTO) {
    Disbursement disbursement = disbursementMapper.toEntity(disbursementDTO);
    List<Disbursement> disbursementExists =
        disbursementRepository.findByStatusNotAndApplicationId(
            DisbursementStatus.REJECTED, UUID.fromString(disbursementDTO.getApplication().getId()));
    if (disbursementExists.size() > 0) {
      throw new CommonException(
          ErrorType.DUPLICATED_REQUEST, "A disbursement request has already been submitted.");
    }
    disbursement.setId(null);
    disbursement.setStatus(DisbursementStatus.PENDING);
    return disbursementMapper.toDTO(disbursementRepository.save(disbursement));
  }

  public DisbursementDTO update(UUID id, DisbursementDTO disbursementDTO) {
    Disbursement disbursement =
        disbursementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DisbursementDTO not found"));
    disbursementDTO.setStatus(null);
    disbursementMapper.updateEntity(disbursementDTO, disbursement);
    return disbursementMapper.toDTO(disbursementRepository.save(disbursement));
  }

  public void delete(UUID id) {
    disbursementRepository.deleteById(id);
  }

  public DisbursementDTO getById(UUID id) {
    Disbursement disbursement =
        disbursementRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DisbursementDTO not found"));
    return disbursementMapper.toDTO(disbursement);
  }

  public List<DisbursementDTO> getAll() {
    return disbursementRepository.findAll().stream()
        .map(disbursementMapper::toDTO)
        .collect(Collectors.toList());
  }
}
