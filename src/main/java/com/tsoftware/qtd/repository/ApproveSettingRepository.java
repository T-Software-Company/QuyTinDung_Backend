package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.entity.ApproveSetting;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveSettingRepository
    extends JpaRepository<ApproveSetting, UUID>, JpaSpecificationExecutor<ApproveSetting> {
  Optional<ApproveSetting> findByTransactionType(TransactionType transactionType);
}
