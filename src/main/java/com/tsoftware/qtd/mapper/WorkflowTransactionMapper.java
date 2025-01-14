package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionResponse;
import com.tsoftware.qtd.entity.WorkflowTransaction;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {EmployeeMapper.class},
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface WorkflowTransactionMapper {
  WorkflowTransactionDTO toDTO(WorkflowTransaction workflowTransaction);

  WorkflowTransaction toEntity(WorkflowTransactionDTO workflowTransactionDTO);

  WorkflowTransactionResponse toResponse(WorkflowTransaction workflowTransaction);
}
