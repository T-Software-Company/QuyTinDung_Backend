package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.workflow.OnboardingWorkflowDTO;
import com.tsoftware.qtd.dto.workflow.OnboardingWorkflowResponse;
import com.tsoftware.qtd.entity.OnboardingWorkflow;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface OnboardingWorkflowMapper {
  OnboardingWorkflowDTO toDTO(OnboardingWorkflow entity);

  OnboardingWorkflow toEntity(OnboardingWorkflowDTO dto);

  OnboardingWorkflowResponse toResponse(OnboardingWorkflow entity);
}
