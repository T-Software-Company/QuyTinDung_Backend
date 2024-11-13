package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.PassPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassPortRepository extends JpaRepository<PassPort, Long> {}
