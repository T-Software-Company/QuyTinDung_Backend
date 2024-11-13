package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.CreditRatingDto;
import java.util.List;

public interface CreditRatingService {
  CreditRatingDto create(CreditRatingDto creditratingDto);

  CreditRatingDto update(Long id, CreditRatingDto creditratingDto);

  void delete(Long id);

  CreditRatingDto getById(Long id);

  List<CreditRatingDto> getAll();
}
