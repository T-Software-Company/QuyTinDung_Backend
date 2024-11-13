package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.CreditRatingDto;
import com.tsoftware.qtd.entity.CreditRating;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CreditRatingMapper;
import com.tsoftware.qtd.repository.CreditRatingRepository;
import com.tsoftware.qtd.service.CreditRatingService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditRatingServiceImpl implements CreditRatingService {

  @Autowired private CreditRatingRepository creditratingRepository;

  @Autowired private CreditRatingMapper creditratingMapper;

  @Override
  public CreditRatingDto create(CreditRatingDto creditratingDto) {
    CreditRating creditrating = creditratingMapper.toEntity(creditratingDto);
    return creditratingMapper.toDto(creditratingRepository.save(creditrating));
  }

  @Override
  public CreditRatingDto update(Long id, CreditRatingDto creditratingDto) {
    CreditRating creditrating =
        creditratingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("CreditRating not found"));
    creditratingMapper.updateEntity(creditratingDto, creditrating);
    return creditratingMapper.toDto(creditratingRepository.save(creditrating));
  }

  @Override
  public void delete(Long id) {
    creditratingRepository.deleteById(id);
  }

  @Override
  public CreditRatingDto getById(Long id) {
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
