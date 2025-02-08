package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.entity.ApprovalSetting;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalSettingRepository
    extends JpaRepository<ApprovalSetting, UUID>, JpaSpecificationExecutor<ApprovalSetting> {
  Optional<ApprovalSetting> findByProcessType(ProcessType processType);
}
