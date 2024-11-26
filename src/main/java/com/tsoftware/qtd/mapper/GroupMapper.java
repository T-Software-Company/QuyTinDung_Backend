package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GroupMapper {
  Group toEntity(GroupRequest dto);

  GroupResponse toResponse(Group entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(GroupRequest dto, @MappingTarget Group entity);

  default Role toRole(com.tsoftware.qtd.constants.EnumType.Role role) {
    return Role.builder().name(role.name()).description(role.getDescription()).build();
  }

  default com.tsoftware.qtd.constants.EnumType.Role toRole(Role role) {
    return com.tsoftware.qtd.constants.EnumType.Role.valueOf(role.getName());
  }
}
