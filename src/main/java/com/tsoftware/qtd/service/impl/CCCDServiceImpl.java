package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.customer.CCCDDto;
import com.tsoftware.qtd.entity.CCCD;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CCCDMapper;
import com.tsoftware.qtd.repository.CCCDRepository;
import com.tsoftware.qtd.service.CCCDService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CCCDServiceImpl implements CCCDService {

  @Autowired private CCCDRepository cccdRepository;

  @Autowired private CCCDMapper cccdMapper;

  @Override
  public CCCDDto create(CCCDDto cccdDto) {
    CCCD cccd = cccdMapper.toEntity(cccdDto);
    return cccdMapper.toDto(cccdRepository.save(cccd));
  }

  @Override
  public CCCDDto update(Long id, CCCDDto cccdDto) {
    CCCD cccd =
        cccdRepository.findById(id).orElseThrow(() -> new NotFoundException("CCCD not found"));
    cccdMapper.updateEntity(cccdDto, cccd);
    return cccdMapper.toDto(cccdRepository.save(cccd));
  }

  @Override
  public void delete(Long id) {
    cccdRepository.deleteById(id);
  }

  @Override
  public CCCDDto getById(Long id) {
    CCCD cccd =
        cccdRepository.findById(id).orElseThrow(() -> new NotFoundException("CCCD not found"));
    return cccdMapper.toDto(cccd);
  }

  @Override
  public List<CCCDDto> getAll() {
    return cccdRepository.findAll().stream().map(cccdMapper::toDto).collect(Collectors.toList());
  }
}
