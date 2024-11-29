package com.tsoftware.qtd.service.impl;

import com.tsoftware.commonlib.constant.WorkflowStatus;
import com.tsoftware.commonlib.model.OnboardingWorkflow;
import com.tsoftware.commonlib.service.WorkflowStorage;
import com.tsoftware.qtd.entity.OnboardingWorkflowEntity;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.OnboardingWorkflowRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class WorkflowServiceImpl implements WorkflowStorage<OnboardingWorkflow> {

  private static final String UNKNOWN_STEP = "unknown";

  @Autowired OnboardingWorkflowRepository onboardingWorkflowRepository;
  @Autowired DtoMapper mapper;

  @Override
  public List<OnboardingWorkflow> getByStatus(UUID targetUUID, WorkflowStatus status) {
    return onboardingWorkflowRepository
        .findAllByTargetUuidAndWorkflowStatus(targetUUID, status)
        .stream()
        .map(mapper::toWorkflow)
        .toList();
  }

  @Override
  public OnboardingWorkflow save(OnboardingWorkflow workflow) {
    var entity =
        onboardingWorkflowRepository
            .findByTargetUuid(workflow.getTargetId())
            .orElse(new OnboardingWorkflowEntity());
    mapper.updateEntity(entity, workflow);
    onboardingWorkflowRepository.save(entity);
    return workflow;
  }
}
