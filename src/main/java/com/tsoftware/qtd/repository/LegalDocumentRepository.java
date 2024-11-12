package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalDocumentRepository extends JpaRepository<LegalDocument, Long> {}
