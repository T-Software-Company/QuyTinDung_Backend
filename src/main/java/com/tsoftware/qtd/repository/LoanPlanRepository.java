package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.LoanPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPlanRepository extends JpaRepository<LoanPlan, Long> {}
