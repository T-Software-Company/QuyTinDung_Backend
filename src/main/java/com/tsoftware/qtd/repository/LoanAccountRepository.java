package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.LoanAccount;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAccountRepository
    extends JpaRepository<LoanAccount, UUID>, JpaSpecificationExecutor<LoanAccount> {
  boolean existsByAccountNumber(String accountNumber);

  Optional<LoanAccount> findByAccountNumber(String accountNumber);
}
