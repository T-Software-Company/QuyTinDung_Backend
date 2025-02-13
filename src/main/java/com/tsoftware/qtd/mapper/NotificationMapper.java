package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.NotificationDTO;
import com.tsoftware.qtd.dto.notification.NotificationRequest;
import com.tsoftware.qtd.dto.notification.NotificationResponse;
import com.tsoftware.qtd.entity.Notification;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
  Notification toEntity(NotificationDTO DTO);

  NotificationDTO toDTO(Notification entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(NotificationDTO DTO, @MappingTarget Notification entity);

  Notification toEntity(NotificationRequest notificationRequest);

  NotificationResponse toResponse(Notification entity);
}
