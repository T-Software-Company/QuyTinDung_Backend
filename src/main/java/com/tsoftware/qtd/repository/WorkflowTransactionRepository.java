package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.entity.WorkflowTransaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTransactionRepository
    extends JpaRepository<WorkflowTransaction, UUID>,
        JpaSpecificationExecutor<WorkflowTransaction> {
  boolean existsByApplicationIdAndType(UUID id, TransactionType type);
}
