package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.DebtNotificationDto;
import com.tsoftware.qtd.entity.DebtNotification;
import com.tsoftware.qtd.mapper.DebtNotificationMapper;
import com.tsoftware.qtd.repository.DebtNotificationRepository;
import com.tsoftware.qtd.service.DebtNotificationService;
import java.util.List;
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
  public DebtNotificationDto update(Long id, DebtNotificationDto debtnotificationDto) {
    DebtNotification debtnotification =
        debtnotificationRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("DebtNotification not found"));
    debtnotificationMapper.updateEntity(debtnotificationDto, debtnotification);
    return debtnotificationMapper.toDto(debtnotificationRepository.save(debtnotification));
  }

  @Override
  public void delete(Long id) {
    debtnotificationRepository.deleteById(id);
  }

  @Override
  public DebtNotificationDto getById(Long id) {
    DebtNotification debtnotification =
        debtnotificationRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("DebtNotification not found"));
    return debtnotificationMapper.toDto(debtnotification);
  }

  @Override
  public List<DebtNotificationDto> getAll() {
    return debtnotificationRepository.findAll().stream()
        .map(debtnotificationMapper::toDto)
        .collect(Collectors.toList());
  }
}
