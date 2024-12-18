package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.ValuationMeeting;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuationMeetingRepository
    extends JpaRepository<ValuationMeeting, UUID>, JpaSpecificationExecutor<ValuationMeeting> {
  Optional<ValuationMeeting> findByApplicationId(UUID creditId);
}
