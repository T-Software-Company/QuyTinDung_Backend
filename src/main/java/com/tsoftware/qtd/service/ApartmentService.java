package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.ApartmentRequest;
import com.tsoftware.qtd.entity.Apartment;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApartmentMapper;
import com.tsoftware.qtd.repository.ApartmentRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApartmentService {

  private final ApartmentRepository apartmentRepository;

  private final ApartmentMapper apartmentMapper;

  public ApartmentRequest create(ApartmentRequest apartmentRequest) {
    Apartment apartment = apartmentMapper.toEntity(apartmentRequest);
    return apartmentMapper.toDTO(apartmentRepository.save(apartment));
  }

  public ApartmentRequest update(UUID id, ApartmentRequest apartmentRequest) {
    Apartment apartment =
        apartmentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Apartment not found"));
    apartmentMapper.updateEntity(apartmentRequest, apartment);
    return apartmentMapper.toDTO(apartmentRepository.save(apartment));
  }

  public void delete(UUID id) {
    apartmentRepository.deleteById(id);
  }

  public ApartmentRequest getById(UUID id) {
    Apartment apartment =
        apartmentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Apartment not found"));
    return apartmentMapper.toDTO(apartment);
  }

  public List<ApartmentRequest> getAll() {
    return apartmentRepository.findAll().stream()
        .map(apartmentMapper::toDTO)
        .collect(Collectors.toList());
  }
}
