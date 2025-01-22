package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.entity.ApprovalProcess;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {EmployeeMapper.class},
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface ApprovalProcessMapper {
  ApprovalProcessDTO toDTO(ApprovalProcess approvalProcess);

  ApprovalProcess toEntity(ApprovalProcessDTO approvalProcessDTO);

  ApprovalProcessResponse toResponse(ApprovalProcess approvalProcess);

  ApprovalProcessResponse toResponse(ApprovalProcessDTO workflowTransaction);

  void updateEntity(@MappingTarget ApprovalProcess entity, ApprovalProcessDTO approvalProcessDTO);
}
