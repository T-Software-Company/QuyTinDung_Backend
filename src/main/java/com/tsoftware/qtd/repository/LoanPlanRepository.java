package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.LoanPlan;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPlanRepository
    extends JpaRepository<LoanPlan, UUID>, JpaSpecificationExecutor<LoanPlan> {
  Optional<LoanPlan> findByApplicationId(UUID applicationId);
}
