package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.EmployeeNotificationDTO;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationRequest;
import com.tsoftware.qtd.dto.notification.EmployeeNotificationResponse;
import com.tsoftware.qtd.entity.EmployeeNotification;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeNotificationMapper {
  EmployeeNotification toEntity(EmployeeNotificationDTO DTO);

  EmployeeNotificationDTO toDTO(EmployeeNotification entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(EmployeeNotificationDTO DTO, @MappingTarget EmployeeNotification entity);

  EmployeeNotification toEntity(EmployeeNotificationRequest employeeNotification);

  EmployeeNotificationResponse toResponse(EmployeeNotification savedEntity);
}
