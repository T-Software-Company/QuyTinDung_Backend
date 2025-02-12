package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.notification.EmployeeNotificationRequest;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationResponse;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.EmployeeNotificationMapper;
import com.tsoftware.qtd.repository.EmployeeNotificationRepository;
import com.tsoftware.qtd.repository.EmployeeRepository;
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
public class EmployeeNotificationService {

  private final EmployeeNotificationRepository employeeNotificationRepository;
  private final EmployeeNotificationMapper employeeNotificationMapper;
  private final EmployeeRepository employeeRepository;
  private final NotificationRepository notificationRepository;

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
}
