package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Machinery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineryRepository extends JpaRepository<Machinery, Long> {}
