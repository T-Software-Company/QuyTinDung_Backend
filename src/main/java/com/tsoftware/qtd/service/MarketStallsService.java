package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.MarketStallsDto;
import java.util.List;

public interface MarketStallsService {
  MarketStallsDto create(MarketStallsDto marketstallsDto);

  MarketStallsDto update(Long id, MarketStallsDto marketstallsDto);

  void delete(Long id);

  MarketStallsDto getById(Long id);

  List<MarketStallsDto> getAll();
}
