package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.entity.ApprovalProcess;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalProcessRepository
    extends JpaRepository<ApprovalProcess, UUID>, JpaSpecificationExecutor<ApprovalProcess> {
  boolean existsByApplicationIdAndType(UUID id, ProcessType type);

  Optional<ApprovalProcess> findByApplicationIdAndType(UUID applicationId, ProcessType processType);
}
