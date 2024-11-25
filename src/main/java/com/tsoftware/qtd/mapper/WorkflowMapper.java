package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.entity.OnboardingWorkflowEntity;
import com.tsoftware.qtd.model.OnboardingWorkflow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkflowMapper {

  @Mapping(target = "statusUpdatedTime", source = "updatedAt")
  OnboardingWorkflow toWorkflow(OnboardingWorkflowEntity workflow);

  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget OnboardingWorkflowEntity entity, OnboardingWorkflow workflow);
}
