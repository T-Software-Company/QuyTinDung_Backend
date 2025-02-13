package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationRequest;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationResponse;
import com.tsoftware.qtd.entity.EmployeeNotification;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.EmployeeNotificationMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.EmployeeNotificationRepository;
import com.tsoftware.qtd.repository.EmployeeRepository;
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
public class EmployeeNotificationService {

  private final EmployeeNotificationRepository employeeNotificationRepository;
  private final EmployeeNotificationMapper employeeNotificationMapper;
  private final EmployeeRepository employeeRepository;
  private final NotificationRepository notificationRepository;
  private final PageResponseMapper pageResponseMapper;

  public EmployeeNotificationResponse create(EmployeeNotificationRequest employeeNotification) {
    var entity = employeeNotificationMapper.toEntity(employeeNotification);
    var employeeId = entity.getEmployee().getId();
    var notificationId = entity.getNotification().getId();
    var employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, employeeId));
    var notification =
        notificationRepository
            .findById(notificationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, notificationId));
    entity.setCreatedAt(ZonedDateTime.now());
    entity.setEmployee(employee);
    entity.setNotification(notification);
    var savedEntity = employeeNotificationRepository.save(entity);
    return employeeNotificationMapper.toResponse(savedEntity);
  }

  public List<EmployeeNotificationResponse> getByNotificationId(UUID notificationId) {
    return employeeNotificationRepository.findByNotificationId(notificationId).stream()
        .map(employeeNotificationMapper::toResponse)
        .toList();
  }

  public PageResponse<EmployeeNotificationResponse> getByEmployeeId(
      UUID employeeId, Pageable pageable) {
    var result =
        employeeNotificationRepository
            .findByEmployeeId(employeeId, pageable)
            .map(employeeNotificationMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

  public PageResponse<EmployeeNotificationResponse> getByUserId(String userId, Pageable pageable) {
    var result =
        employeeNotificationRepository
            .findByEmployeeUserId(userId, pageable)
            .map(employeeNotificationMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

  public PageResponse<EmployeeNotificationResponse> getAll(
      Specification<EmployeeNotification> spec, Pageable pageable) {
    var result =
        employeeNotificationRepository
            .findAll(spec, pageable)
            .map(employeeNotificationMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }
}
