package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.MarketStallsDto;
import com.tsoftware.qtd.entity.MarketStalls;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.MarketStallsMapper;
import com.tsoftware.qtd.repository.MarketStallsRepository;
import com.tsoftware.qtd.service.MarketStallsService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketStallsServiceImpl implements MarketStallsService {

  @Autowired private MarketStallsRepository marketstallsRepository;

  @Autowired private MarketStallsMapper marketstallsMapper;

  @Override
  public MarketStallsDto create(MarketStallsDto marketstallsDto) {
    MarketStalls marketstalls = marketstallsMapper.toEntity(marketstallsDto);
    return marketstallsMapper.toDto(marketstallsRepository.save(marketstalls));
  }

  @Override
  public MarketStallsDto update(Long id, MarketStallsDto marketstallsDto) {
    MarketStalls marketstalls =
        marketstallsRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("MarketStalls not found"));
    marketstallsMapper.updateEntity(marketstallsDto, marketstalls);
    return marketstallsMapper.toDto(marketstallsRepository.save(marketstalls));
  }

  @Override
  public void delete(Long id) {
    marketstallsRepository.deleteById(id);
  }

  @Override
  public MarketStallsDto getById(Long id) {
    MarketStalls marketstalls =
        marketstallsRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("MarketStalls not found"));
    return marketstallsMapper.toDto(marketstalls);
  }

  @Override
  public List<MarketStallsDto> getAll() {
    return marketstallsRepository.findAll().stream()
        .map(marketstallsMapper::toDto)
        .collect(Collectors.toList());
  }
}
