package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.FinancialInfo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialInfoRepository
    extends JpaRepository<FinancialInfo, UUID>, JpaSpecificationExecutor<FinancialInfo> {}
