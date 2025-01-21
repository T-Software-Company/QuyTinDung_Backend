package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.MarketStallsRequest;
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

  public MarketStallsRequest create(MarketStallsRequest marketstallsRequest) {
    MarketStalls marketstalls = marketstallsMapper.toEntity(marketstallsRequest);
    return marketstallsMapper.toDTO(marketstallsRepository.save(marketstalls));
  }

  public MarketStallsRequest update(UUID id, MarketStallsRequest marketstallsRequest) {
    MarketStalls marketstalls =
        marketstallsRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("MarketStalls not found"));
    marketstallsMapper.updateEntity(marketstallsRequest, marketstalls);
    return marketstallsMapper.toDTO(marketstallsRepository.save(marketstalls));
  }

  public void delete(UUID id) {
    marketstallsRepository.deleteById(id);
  }

  public MarketStallsRequest getById(UUID id) {
    MarketStalls marketstalls =
        marketstallsRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("MarketStalls not found"));
    return marketstallsMapper.toDTO(marketstalls);
  }

  public List<MarketStallsRequest> getAll() {
    return marketstallsRepository.findAll().stream()
        .map(marketstallsMapper::toDTO)
        .collect(Collectors.toList());
  }
}
