package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.customer.PassPortDto;
import com.tsoftware.qtd.entity.PassPort;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.PassPortMapper;
import com.tsoftware.qtd.repository.PassPortRepository;
import com.tsoftware.qtd.service.PassPortService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassPortServiceImpl implements PassPortService {

  @Autowired private PassPortRepository passportRepository;

  @Autowired private PassPortMapper passportMapper;

  @Override
  public PassPortDto create(PassPortDto passportDto) {
    PassPort passport = passportMapper.toEntity(passportDto);
    return passportMapper.toDto(passportRepository.save(passport));
  }

  @Override
  public PassPortDto update(UUID id, PassPortDto passportDto) {
    PassPort passport =
        passportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("PassPort not found"));
    passportMapper.updateEntity(passportDto, passport);
    return passportMapper.toDto(passportRepository.save(passport));
  }

  @Override
  public void delete(UUID id) {
    passportRepository.deleteById(id);
  }

  @Override
  public PassPortDto getById(UUID id) {
    PassPort passport =
        passportRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("PassPort not found"));
    return passportMapper.toDto(passport);
  }

  @Override
  public List<PassPortDto> getAll() {
    return passportRepository.findAll().stream()
        .map(passportMapper::toDto)
        .collect(Collectors.toList());
  }
}
