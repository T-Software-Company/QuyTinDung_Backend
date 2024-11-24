package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Group;
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
}
