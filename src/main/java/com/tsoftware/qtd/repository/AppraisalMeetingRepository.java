package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.AppraisalMeeting;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraisalMeetingRepository
    extends JpaRepository<AppraisalMeeting, UUID>, JpaSpecificationExecutor<AppraisalMeeting> {}
