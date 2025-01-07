package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.entity.Approve;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface ApproveMapper {
  Approve toEntity(ApproveResponse dto);

  ApproveResponse toDto(Approve entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ApproveResponse dto, @MappingTarget Approve entity);
}
