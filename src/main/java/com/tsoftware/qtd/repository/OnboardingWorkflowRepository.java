package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.entity.OnboardingWorkflow;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingWorkflowRepository
    extends JpaRepository<OnboardingWorkflow, UUID>, JpaSpecificationExecutor<OnboardingWorkflow> {
  List<OnboardingWorkflow> findByTargetIdAndStatus(UUID id, WorkflowStatus status);

  Optional<OnboardingWorkflow> findByTargetId(UUID targetUuid);

  Optional<OnboardingWorkflow> findByStepTransactionId();
}
