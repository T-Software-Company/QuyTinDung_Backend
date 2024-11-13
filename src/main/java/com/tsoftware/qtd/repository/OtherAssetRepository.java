package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.OtherAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherAssetRepository extends JpaRepository<OtherAsset, Long> {}
