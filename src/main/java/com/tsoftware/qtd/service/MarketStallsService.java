package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.MarketStallsDto;
import com.tsoftware.qtd.entity.MarketStalls;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.MarketStallsMapper;
import com.tsoftware.qtd.repository.MarketStallsRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketStallsService {

  private final MarketStallsRepository marketstallsRepository;

  private final MarketStallsMapper marketstallsMapper;

  public MarketStallsDto create(MarketStallsDto marketstallsDto) {
    MarketStalls marketstalls = marketstallsMapper.toEntity(marketstallsDto);
    return marketstallsMapper.toDTO(marketstallsRepository.save(marketstalls));
  }

  public MarketStallsDto update(UUID id, MarketStallsDto marketstallsDto) {
    MarketStalls marketstalls =
        marketstallsRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("MarketStalls not found"));
    marketstallsMapper.updateEntity(marketstallsDto, marketstalls);
    return marketstallsMapper.toDTO(marketstallsRepository.save(marketstalls));
  }

  public void delete(UUID id) {
    marketstallsRepository.deleteById(id);
  }

  public MarketStallsDto getById(UUID id) {
    MarketStalls marketstalls =
        marketstallsRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("MarketStalls not found"));
    return marketstallsMapper.toDTO(marketstalls);
  }

  public List<MarketStallsDto> getAll() {
    return marketstallsRepository.findAll().stream()
        .map(marketstallsMapper::toDTO)
        .collect(Collectors.toList());
  }
}
