package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import java.util.List;

public interface DebtNotificationService {
  DebtNotificationDto create(DebtNotificationDto debtnotificationDto);

  DebtNotificationDto update(Long id, DebtNotificationDto debtnotificationDto);

  void delete(Long id);

  DebtNotificationDto getById(Long id);

  List<DebtNotificationDto> getAll();
}
