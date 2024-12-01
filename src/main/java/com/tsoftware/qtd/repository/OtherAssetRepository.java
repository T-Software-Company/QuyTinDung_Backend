package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.OtherAsset;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherAssetRepository
    extends JpaRepository<OtherAsset, UUID>, JpaSpecificationExecutor<OtherAsset> {}
