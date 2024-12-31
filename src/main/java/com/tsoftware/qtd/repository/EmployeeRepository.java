package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Employee;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
    extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
  Optional<Employee> findByUserId(String userId);

  boolean existsByEmail(String mail);

  boolean existsByUsername(String username);

  Set<Employee> findByUserIdIn(Set<String> assignees);

  List<Employee> findByIdIn(List<UUID> ids);
}
