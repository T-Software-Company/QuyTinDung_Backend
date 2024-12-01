package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import java.util.List;
import java.util.UUID;

public interface DebtNotificationService {
  DebtNotificationDto create(DebtNotificationDto debtnotificationDto);

  DebtNotificationDto update(UUID id, DebtNotificationDto debtnotificationDto);

  void delete(UUID id);

  DebtNotificationDto getById(UUID id);

  List<DebtNotificationDto> getAll();
}
