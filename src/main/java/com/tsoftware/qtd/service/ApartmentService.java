package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.ApartmentDTO;
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

  public ApartmentDTO create(ApartmentDTO apartmentDTO) {
    Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
    return apartmentMapper.toDTO(apartmentRepository.save(apartment));
  }

  public ApartmentDTO update(UUID id, ApartmentDTO apartmentDTO) {
    Apartment apartment =
        apartmentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Apartment not found"));
    apartmentMapper.updateEntity(apartmentDTO, apartment);
    return apartmentMapper.toDTO(apartmentRepository.save(apartment));
  }

  public void delete(UUID id) {
    apartmentRepository.deleteById(id);
  }

  public ApartmentDTO getById(UUID id) {
    Apartment apartment =
        apartmentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Apartment not found"));
    return apartmentMapper.toDTO(apartment);
  }

  public List<ApartmentDTO> getAll() {
    return apartmentRepository.findAll().stream()
        .map(apartmentMapper::toDTO)
        .collect(Collectors.toList());
  }
}
