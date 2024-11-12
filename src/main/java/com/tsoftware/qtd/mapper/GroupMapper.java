package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.GroupDto;
import com.tsoftware.qtd.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupMapper {
  Group toEntity(GroupDto dto);

  GroupDto toDto(Group entity);

  void updateEntity(GroupDto dto, @MappingTarget Group entity);
}
