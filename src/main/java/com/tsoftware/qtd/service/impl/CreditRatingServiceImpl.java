package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.application.CreditRatingDto;
import com.tsoftware.qtd.entity.CreditRating;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CreditRatingMapper;
import com.tsoftware.qtd.repository.CreditRatingRepository;
import com.tsoftware.qtd.service.CreditRatingService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreditRatingServiceImpl implements CreditRatingService {

  private final CreditRatingRepository creditratingRepository;

  private final CreditRatingMapper creditratingMapper;

  @Override
  public CreditRatingDto create(CreditRatingDto creditratingDto) {
    CreditRating creditrating = creditratingMapper.toEntity(creditratingDto);
    return creditratingMapper.toDto(creditratingRepository.save(creditrating));
  }

  @Override
  public CreditRatingDto update(UUID id, CreditRatingDto creditratingDto) {
    CreditRating creditrating =
        creditratingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("CreditRating not found"));
    creditratingMapper.updateEntity(creditratingDto, creditrating);
    return creditratingMapper.toDto(creditratingRepository.save(creditrating));
  }

  @Override
  public void delete(UUID id) {
    creditratingRepository.deleteById(id);
  }

  @Override
  public CreditRatingDto getById(UUID id) {
    CreditRating creditrating =
        creditratingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("CreditRating not found"));
    return creditratingMapper.toDto(creditrating);
  }

  @Override
  public List<CreditRatingDto> getAll() {
    return creditratingRepository.findAll().stream()
        .map(creditratingMapper::toDto)
        .collect(Collectors.toList());
  }
}
