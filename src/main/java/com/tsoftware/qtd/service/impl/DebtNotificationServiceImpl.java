package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import com.tsoftware.qtd.entity.DebtNotification;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.DebtNotificationMapper;
import com.tsoftware.qtd.repository.DebtNotificationRepository;
import com.tsoftware.qtd.service.DebtNotificationService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebtNotificationServiceImpl implements DebtNotificationService {

  @Autowired private DebtNotificationRepository debtnotificationRepository;

  @Autowired private DebtNotificationMapper debtnotificationMapper;

  @Override
  public DebtNotificationDto create(DebtNotificationDto debtnotificationDto) {
    DebtNotification debtnotification = debtnotificationMapper.toEntity(debtnotificationDto);
    return debtnotificationMapper.toDto(debtnotificationRepository.save(debtnotification));
  }

  @Override
  public DebtNotificationDto update(UUID id, DebtNotificationDto debtnotificationDto) {
    DebtNotification debtnotification =
        debtnotificationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DebtNotification not found"));
    debtnotificationMapper.updateEntity(debtnotificationDto, debtnotification);
    return debtnotificationMapper.toDto(debtnotificationRepository.save(debtnotification));
  }

  @Override
  public void delete(UUID id) {
    debtnotificationRepository.deleteById(id);
  }

  @Override
  public DebtNotificationDto getById(UUID id) {
    DebtNotification debtnotification =
        debtnotificationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DebtNotification not found"));
    return debtnotificationMapper.toDto(debtnotification);
  }

  @Override
  public List<DebtNotificationDto> getAll() {
    return debtnotificationRepository.findAll().stream()
        .map(debtnotificationMapper::toDto)
        .collect(Collectors.toList());
  }
}
