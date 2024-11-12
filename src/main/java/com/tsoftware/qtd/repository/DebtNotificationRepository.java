package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.DebtNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtNotificationRepository extends JpaRepository<DebtNotification, Long> {}
