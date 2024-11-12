package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.IncomeProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeProofRepository extends JpaRepository<IncomeProof, Long> {}
