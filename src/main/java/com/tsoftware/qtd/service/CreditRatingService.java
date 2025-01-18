package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.CreditRatingDTO;
import com.tsoftware.qtd.entity.CreditRating;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CreditRatingMapper;
import com.tsoftware.qtd.repository.CreditRatingRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreditRatingService {

  private final CreditRatingRepository creditratingRepository;

  private final CreditRatingMapper creditratingMapper;

  public CreditRatingDTO create(CreditRatingDTO creditratingDTO) {
    CreditRating creditrating = creditratingMapper.toEntity(creditratingDTO);
    return creditratingMapper.toDTO(creditratingRepository.save(creditrating));
  }

  public CreditRatingDTO update(UUID id, CreditRatingDTO creditratingDTO) {
    CreditRating creditrating =
        creditratingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("CreditRating not found"));
    creditratingMapper.updateEntity(creditratingDTO, creditrating);
    return creditratingMapper.toDTO(creditratingRepository.save(creditrating));
  }

  public void delete(UUID id) {
    creditratingRepository.deleteById(id);
  }

  public CreditRatingDTO getById(UUID id) {
    CreditRating creditrating =
        creditratingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("CreditRating not found"));
    return creditratingMapper.toDTO(creditrating);
  }

  public List<CreditRatingDTO> getAll() {
    return creditratingRepository.findAll().stream()
        .map(creditratingMapper::toDTO)
        .collect(Collectors.toList());
  }
}
