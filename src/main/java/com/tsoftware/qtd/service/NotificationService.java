package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.notification.NotificationRequest;
import com.tsoftware.qtd.dto.notification.NotificationResponse;
import com.tsoftware.qtd.mapper.NotificationMapper;
import com.tsoftware.qtd.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;

  private final NotificationMapper notificationMapper;

  public NotificationResponse create(NotificationRequest notificationRequest) {
    var entity = notificationMapper.toEntity(notificationRequest);
    var saved = notificationRepository.save(entity);
    return notificationMapper.toResponse(saved);
  }
}
