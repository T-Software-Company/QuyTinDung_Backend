package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Approve;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveRepository
    extends JpaRepository<Approve, UUID>, JpaSpecificationExecutor<Approve> {
  List<Approve> findByApproverId(UUID id);
}
