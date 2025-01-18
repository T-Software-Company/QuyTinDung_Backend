package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDTO;
import com.tsoftware.qtd.entity.DebtNotification;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DebtNotificationMapper {
  DebtNotification toEntity(DebtNotificationDTO dto);

  DebtNotificationDTO toDTO(DebtNotification entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(DebtNotificationDTO dto, @MappingTarget DebtNotification entity);
}
