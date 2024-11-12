package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.DebtNotificationDto;
import com.tsoftware.qtd.entity.DebtNotification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DebtNotificationMapper {
  DebtNotification toEntity(DebtNotificationDto dto);

  DebtNotificationDto toDto(DebtNotification entity);

  void updateEntity(DebtNotificationDto dto, @MappingTarget DebtNotification entity);
}
