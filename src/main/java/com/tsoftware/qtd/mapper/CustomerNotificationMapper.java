package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.CustomerNotificationDTO;
import com.tsoftware.qtd.dto.notification.CustomerNotificationRequest;
import com.tsoftware.qtd.dto.notification.CustomerNotificationResponse;
import com.tsoftware.qtd.entity.CustomerNotification;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerNotificationMapper {
  CustomerNotification toEntity(CustomerNotificationDTO DTO);

  CustomerNotificationDTO toDTO(CustomerNotification entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CustomerNotificationDTO DTO, @MappingTarget CustomerNotification entity);

  CustomerNotification toEntity(CustomerNotificationRequest customerNotificationRequest);

  CustomerNotificationResponse toResponse(CustomerNotification entity);
}
