package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.LoanRequest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, UUID> {}
