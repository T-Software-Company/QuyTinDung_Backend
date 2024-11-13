package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.ValuationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuationReportRepository extends JpaRepository<ValuationReport, Long> {}
