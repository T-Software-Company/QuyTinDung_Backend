package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import com.tsoftware.qtd.entity.Disbursement;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DisbursementRepository
    extends JpaRepository<Disbursement, UUID>, JpaSpecificationExecutor<Disbursement> {
  List<Disbursement> findByStatusNotAndApplicationId(DisbursementStatus status, UUID applicationId);
}
