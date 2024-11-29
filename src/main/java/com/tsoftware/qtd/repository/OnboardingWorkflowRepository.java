package com.tsoftware.qtd.repository;

import com.tsoftware.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.entity.OnboardingWorkflowEntity;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingWorkflowRepository
    extends JpaRepository<OnboardingWorkflowEntity, UUID> {
  Set<OnboardingWorkflowEntity> findAllByTargetUuidAndWorkflowStatus(
      UUID id, WorkflowStatus status);

  Optional<OnboardingWorkflowEntity> findByTargetUuid(UUID targetUuid);
}
