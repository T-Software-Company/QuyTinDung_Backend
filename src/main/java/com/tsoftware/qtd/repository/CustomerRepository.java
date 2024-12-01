package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
    extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
  Optional<Customer> findByIdentityInfoIdentifyId(String identifyId);
}
