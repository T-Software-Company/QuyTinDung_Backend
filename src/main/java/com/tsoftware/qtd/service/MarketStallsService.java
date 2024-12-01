package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.MarketStallsDto;
import java.util.List;
import java.util.UUID;

public interface MarketStallsService {
  MarketStallsDto create(MarketStallsDto marketstallsDto);

  MarketStallsDto update(UUID id, MarketStallsDto marketstallsDto);

  void delete(UUID id);

  MarketStallsDto getById(UUID id);

  List<MarketStallsDto> getAll();
}
