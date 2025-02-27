package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.CustomerNotification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerNotificationRepository
    extends JpaRepository<CustomerNotification, UUID>,
        JpaSpecificationExecutor<CustomerNotification> {

  List<CustomerNotification> findByNotificationId(UUID notificationId);

  Page<CustomerNotification> findByCustomerId(UUID customerId, Pageable pageable);

  Page<CustomerNotification> findByCustomerUserId(String userId, Pageable pageable);
}
