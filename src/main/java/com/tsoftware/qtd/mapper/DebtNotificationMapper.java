package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import com.tsoftware.qtd.entity.DebtNotification;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DebtNotificationMapper {
  DebtNotification toEntity(DebtNotificationDto dto);

  DebtNotificationDto toDTO(DebtNotification entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(DebtNotificationDto dto, @MappingTarget DebtNotification entity);
}
