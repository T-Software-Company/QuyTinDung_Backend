package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.entity.Approve;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface ApproveMapper {
  Approve toEntity(ApproveResponse dto);

  ApproveResponse toDto(Approve entity);

  void updateEntity(ApproveResponse dto, @MappingTarget Approve entity);
}
