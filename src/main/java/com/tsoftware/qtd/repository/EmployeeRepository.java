package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Employee;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
    extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
  Optional<Employee> findByUserId(String userId);

  boolean existsByEmail(String mail);
}
