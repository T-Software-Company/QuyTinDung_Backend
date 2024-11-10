package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByUserId(String userId);
}
