package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  @Query("select r from Role r where r.name in :roles")
  List<Role> findAllByName(@Param("roles") List<String> roles);

  boolean existsByName(String name);
}
