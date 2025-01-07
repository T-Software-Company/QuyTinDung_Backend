package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.OnboardingWorkflow;
import com.tsoftware.qtd.commonlib.service.WorkflowStorage;
import com.tsoftware.qtd.entity.OnboardingWorkflowEntity;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.OnboardingWorkflowRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowStorage<OnboardingWorkflow> {

  private final OnboardingWorkflowRepository onboardingWorkflowRepository;
  private final DtoMapper mapper;

  @Override
  public List<OnboardingWorkflow> getByStatus(UUID targetUUID, WorkflowStatus status) {
    return onboardingWorkflowRepository
        .findAllByTargetIdAndWorkflowStatus(targetUUID, status)
        .stream()
        .map(mapper::toWorkflow)
        .toList();
  }

  @Override
  public OnboardingWorkflow save(OnboardingWorkflow workflow) {
    var entity =
        onboardingWorkflowRepository
            .findByTargetId(workflow.getTargetId())
            .orElse(new OnboardingWorkflowEntity());
    mapper.updateEntity(entity, workflow);
    onboardingWorkflowRepository.save(entity);
    return workflow;
  }
}
