package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.ApartmentDto;
import com.tsoftware.qtd.entity.Apartment;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApartmentMapper;
import com.tsoftware.qtd.repository.ApartmentRepository;
import com.tsoftware.qtd.service.ApartmentService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

  private final ApartmentRepository apartmentRepository;

  private final ApartmentMapper apartmentMapper;

  @Override
  public ApartmentDto create(ApartmentDto apartmentDto) {
    Apartment apartment = apartmentMapper.toEntity(apartmentDto);
    return apartmentMapper.toDto(apartmentRepository.save(apartment));
  }

  @Override
  public ApartmentDto update(UUID id, ApartmentDto apartmentDto) {
    Apartment apartment =
        apartmentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Apartment not found"));
    apartmentMapper.updateEntity(apartmentDto, apartment);
    return apartmentMapper.toDto(apartmentRepository.save(apartment));
  }

  @Override
  public void delete(UUID id) {
    apartmentRepository.deleteById(id);
  }

  @Override
  public ApartmentDto getById(UUID id) {
    Apartment apartment =
        apartmentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Apartment not found"));
    return apartmentMapper.toDto(apartment);
  }

  @Override
  public List<ApartmentDto> getAll() {
    return apartmentRepository.findAll().stream()
        .map(apartmentMapper::toDto)
        .collect(Collectors.toList());
  }
}
