package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Asset;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
  List<Asset> findByCreditId(Long id);
}
