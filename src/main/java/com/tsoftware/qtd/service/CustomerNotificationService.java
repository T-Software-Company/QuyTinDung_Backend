package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.notification.CustomerNotificationRequest;
import com.tsoftware.qtd.dto.notification.CustomerNotificationResponse;
import com.tsoftware.qtd.entity.CustomerNotification;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.CustomerNotificationMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.CustomerNotificationRepository;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.repository.NotificationRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
  private final PageResponseMapper pageResponseMapper;

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

  public PageResponse<CustomerNotificationResponse> getByCustomerId(
      UUID customerId, Pageable pageable) {
    var result =
        customerNotificationRepository
            .findByCustomerId(customerId, pageable)
            .map(customerNotificationMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

  public PageResponse<CustomerNotificationResponse> getAll(
      Specification<CustomerNotification> spec, Pageable pageable) {
    var result =
        customerNotificationRepository
            .findAll(spec, pageable)
            .map(customerNotificationMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

  public PageResponse<CustomerNotificationResponse> getByUserId(String userId, Pageable pageable) {
    var result =
        customerNotificationRepository
            .findByCustomerUserId(userId, pageable)
            .map(customerNotificationMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

  public void read(UUID id) {
    var entity =
        customerNotificationRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    entity.setIsRead(true);
    entity.setReadAt(ZonedDateTime.now());
    customerNotificationRepository.save(entity);
  }

  public void unRead(UUID id) {
    var entity =
        customerNotificationRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    entity.setIsRead(false);
    entity.setReadAt(null);
    customerNotificationRepository.save(entity);
  }

  public void read(List<UUID> ids) {
    var entities = customerNotificationRepository.findAllById(ids);
    entities.forEach(
        e -> {
          e.setIsRead(true);
          e.setReadAt(ZonedDateTime.now());
        });
    customerNotificationRepository.saveAll(entities);
  }

  public void unRead(List<UUID> ids) {
    var entities = customerNotificationRepository.findAllById(ids);
    entities.forEach(
        e -> {
          e.setIsRead(false);
          e.setReadAt(null);
        });
    customerNotificationRepository.saveAll(entities);
  }
}
