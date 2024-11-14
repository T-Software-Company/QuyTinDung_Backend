package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Approve;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveRepository extends JpaRepository<Approve, Long> {
  List<Approve> findByApproverId(Long id);
}
