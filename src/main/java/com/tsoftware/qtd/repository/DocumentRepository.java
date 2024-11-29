package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Document;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
  Set<Document> findByCustomerId(Long id);

  Set<Document> findByUrlIn(Set<String> urls);
}
