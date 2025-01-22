package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.entity.Approval;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface ApproveMapper {
  Approval toEntity(ApprovalResponse dto);

  ApprovalResponse toDTO(Approval entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ApprovalResponse dto, @MappingTarget Approval entity);
}
