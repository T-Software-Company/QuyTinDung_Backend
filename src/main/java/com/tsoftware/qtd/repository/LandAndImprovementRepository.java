package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.LandAndImprovement;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandAndImprovementRepository extends JpaRepository<LandAndImprovement, UUID> {}
