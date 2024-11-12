package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.CreditDto;
import com.tsoftware.qtd.entity.Credit;
import com.tsoftware.qtd.mapper.CreditMapper;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.service.CreditService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService {

  @Autowired private CreditRepository creditRepository;

  @Autowired private CreditMapper creditMapper;

  @Override
  public CreditDto create(CreditDto creditDto) {
    Credit credit = creditMapper.toEntity(creditDto);
    return creditMapper.toDto(creditRepository.save(credit));
  }

  @Override
  public CreditDto update(Long id, CreditDto creditDto) {
    Credit credit =
        creditRepository.findById(id).orElseThrow(() -> new RuntimeException("Credit not found"));
    creditMapper.updateEntity(creditDto, credit);
    return creditMapper.toDto(creditRepository.save(credit));
  }

  @Override
  public void delete(Long id) {
    creditRepository.deleteById(id);
  }

  @Override
  public CreditDto getById(Long id) {
    Credit credit =
        creditRepository.findById(id).orElseThrow(() -> new RuntimeException("Credit not found"));
    return creditMapper.toDto(credit);
  }

  @Override
  public List<CreditDto> getAll() {
    return creditRepository.findAll().stream()
        .map(creditMapper::toDto)
        .collect(Collectors.toList());
  }
}
