package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.LandAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandAssetRepository extends JpaRepository<LandAsset, Long> {}
