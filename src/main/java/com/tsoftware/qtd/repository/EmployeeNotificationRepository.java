package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.EmployeeNotification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeNotificationRepository
    extends JpaRepository<EmployeeNotification, UUID>,
        JpaSpecificationExecutor<EmployeeNotification> {
  List<EmployeeNotification> findByNotificationId(UUID notificationId);

  Page<EmployeeNotification> findByEmployeeId(UUID employeeId, Pageable pageable);

  Page<EmployeeNotification> findByEmployeeUserId(String userId, Pageable pageable);
}
