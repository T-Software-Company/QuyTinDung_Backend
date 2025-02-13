package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.notification.CustomerNotificationRequest;
import com.tsoftware.qtd.dto.notification.CustomerNotificationResponse;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.CustomerNotificationMapper;
import com.tsoftware.qtd.repository.CustomerNotificationRepository;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.repository.NotificationRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerNotificationService {

  private final CustomerNotificationRepository customerNotificationRepository;
  private final CustomerNotificationMapper customerNotificationMapper;
  private final CustomerRepository customerRepository;
  private final NotificationRepository notificationRepository;

  public CustomerNotificationResponse create(
      CustomerNotificationRequest customerNotificationRequest) {
    var entity = customerNotificationMapper.toEntity(customerNotificationRequest);
    var customerId = entity.getCustomer().getId();
    var notificationId = entity.getNotification().getId();
    var customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, customerId));
    var notification =
        notificationRepository
            .findById(notificationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, notificationId));
    entity.setCreatedAt(ZonedDateTime.now());
    entity.setCustomer(customer);
    entity.setNotification(notification);
    var savedEntity = customerNotificationRepository.save(entity);
    return customerNotificationMapper.toResponse(savedEntity);
  }

  public List<CustomerNotificationResponse> getByNotificationId(UUID notificationId) {
    return customerNotificationRepository.findByNotificationId(notificationId).stream()
        .map(customerNotificationMapper::toResponse)
        .toList();
  }
}
