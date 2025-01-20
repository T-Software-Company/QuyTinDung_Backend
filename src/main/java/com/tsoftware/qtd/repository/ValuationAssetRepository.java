package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.ValuationAsset;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuationAssetRepository
    extends JpaRepository<ValuationAsset, UUID>, JpaSpecificationExecutor<ValuationAsset> {}
