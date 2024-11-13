package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.ValuationMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuationMeetingRepository extends JpaRepository<ValuationMeeting, Long> {}
