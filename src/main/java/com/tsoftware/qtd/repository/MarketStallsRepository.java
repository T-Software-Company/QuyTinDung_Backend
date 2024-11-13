package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.MarketStalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketStallsRepository extends JpaRepository<MarketStalls, Long> {}
