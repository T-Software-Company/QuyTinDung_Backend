package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.CCCD;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CCCDRepository extends JpaRepository<CCCD, UUID> {}
