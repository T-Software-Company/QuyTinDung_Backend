package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Credit;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository
    extends JpaRepository<Credit, UUID>, JpaSpecificationExecutor<Credit> {}
