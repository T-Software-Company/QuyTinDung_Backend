package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.CreditRatingDto;
import java.util.List;
import java.util.UUID;

public interface CreditRatingService {
  CreditRatingDto create(CreditRatingDto creditratingDto);

  CreditRatingDto update(UUID id, CreditRatingDto creditratingDto);

  void delete(UUID id);

  CreditRatingDto getById(UUID id);

  List<CreditRatingDto> getAll();
}
