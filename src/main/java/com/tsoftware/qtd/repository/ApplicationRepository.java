package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.entity.Application;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository
    extends JpaRepository<Application, UUID>, JpaSpecificationExecutor<Application> {
  List<Application> findByCustomerId(UUID customerId);

  List<Application> findByCustomerIdAndStatusIn(UUID customerId, List<LoanStatus> status);
}
