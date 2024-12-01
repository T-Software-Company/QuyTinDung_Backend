package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Asset;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository
    extends JpaRepository<Asset, UUID>, JpaSpecificationExecutor<Asset> {
  List<Asset> findByCreditId(UUID id);
}
