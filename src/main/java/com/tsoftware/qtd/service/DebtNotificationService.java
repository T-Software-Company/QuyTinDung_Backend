package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDTO;
import com.tsoftware.qtd.entity.DebtNotification;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.DebtNotificationMapper;
import com.tsoftware.qtd.repository.DebtNotificationRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DebtNotificationService {

  private final DebtNotificationRepository debtnotificationRepository;

  private final DebtNotificationMapper debtnotificationMapper;

  public DebtNotificationDTO create(DebtNotificationDTO debtnotificationDTO) {
    DebtNotification debtnotification = debtnotificationMapper.toEntity(debtnotificationDTO);
    return debtnotificationMapper.toDTO(debtnotificationRepository.save(debtnotification));
  }

  public DebtNotificationDTO update(UUID id, DebtNotificationDTO debtnotificationDTO) {
    DebtNotification debtnotification =
        debtnotificationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DebtNotification not found"));
    debtnotificationMapper.updateEntity(debtnotificationDTO, debtnotification);
    return debtnotificationMapper.toDTO(debtnotificationRepository.save(debtnotification));
  }

  public void delete(UUID id) {
    debtnotificationRepository.deleteById(id);
  }

  public DebtNotificationDTO getById(UUID id) {
    DebtNotification debtnotification =
        debtnotificationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("DebtNotification not found"));
    return debtnotificationMapper.toDTO(debtnotification);
  }

  public List<DebtNotificationDTO> getAll() {
    return debtnotificationRepository.findAll().stream()
        .map(debtnotificationMapper::toDTO)
        .collect(Collectors.toList());
  }
}
